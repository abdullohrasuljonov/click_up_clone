package uz.pdp.clickup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.*;
import uz.pdp.clickup.entity.enums.AddType;
import uz.pdp.clickup.entity.enums.WorkspacePermissionName;
import uz.pdp.clickup.entity.enums.WorkspaceRoleName;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.MemberDTO;
import uz.pdp.clickup.payload.WorkspaceDTO;
import uz.pdp.clickup.payload.WorkspaceRoleDto;
import uz.pdp.clickup.repository.*;
import uz.pdp.clickup.service.WorkspaceService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    WorkspaceRepository workspaceRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    WorkspaceUserRepository workspaceUserRepository;
    @Autowired
    WorkspaceRoleRepository workspaceRoleRepository;
    @Autowired
    WorkspacePermissionRepository workspacePermissionRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public ApiResponse addWorkspace(WorkspaceDTO workspaceDTO, User user) {
        //WORKSPACE OCHDIK
        if (workspaceRepository.existsByOwnerIdAndName(user.getId(), workspaceDTO.getName()))
            return new ApiResponse("You already have a workspace with that name!", false);
        Workspace workspace = new Workspace(
                workspaceDTO.getName(),
                workspaceDTO.getColor(),
                user,
                workspaceDTO.getAvatarId() == null ? null : attachmentRepository.findById(workspaceDTO.getAvatarId()).orElseThrow(() -> new ResourceNotFoundException("attachment"))
        );
        workspaceRepository.save(workspace);

        //WORKSPACE ROLE OCHDIK
        WorkspaceRole ownerRole = workspaceRoleRepository.save(new WorkspaceRole(
                workspace,
                WorkspaceRoleName.ROLE_OWNER.name(),
                null
        ));
        WorkspaceRole adminRole = workspaceRoleRepository.save(new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_ADMIN.name(), null));
        WorkspaceRole memberRole = workspaceRoleRepository.save(new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_MEMBER.name(), null));
        WorkspaceRole guestRole = workspaceRoleRepository.save(new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_GUEST.name(), null));


        //OWNERGA HUQUQLARNI BERYAPAMIZ
        WorkspacePermissionName[] workspacePermissionNames = WorkspacePermissionName.values();
        List<WorkspacePermission> workspacePermissions = new ArrayList<>();

        for (WorkspacePermissionName workspacePermissionName : workspacePermissionNames) {
            WorkspacePermission workspacePermission = new WorkspacePermission(
                    ownerRole,
                    workspacePermissionName);
            workspacePermissions.add(workspacePermission);
            if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_ADMIN)) {
                workspacePermissions.add(new WorkspacePermission(
                        adminRole,
                        workspacePermissionName));
            }
            if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_MEMBER)) {
                workspacePermissions.add(new WorkspacePermission(
                        memberRole,
                        workspacePermissionName));
            }
            if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_GUEST)) {
                workspacePermissions.add(new WorkspacePermission(
                        guestRole,
                        workspacePermissionName));
            }

        }
        workspacePermissionRepository.saveAll(workspacePermissions);

        //WORKSPACE USER OCHDIK
        workspaceUserRepository.save(new WorkspaceUser(
                workspace,
                user,
                ownerRole,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())

        ));

        return new ApiResponse("Workspace saved!", true);
    }

    @Override
    public ApiResponse editWorkspace(WorkspaceDTO workspaceDTO, Long id) {
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(id);
        if (!optionalWorkspace.isPresent())
            return new ApiResponse("There isn't workspace such an id!", false);
        Workspace workspace = optionalWorkspace.get();
        if (workspaceRepository.existsByOwnerIdAndNameAndIdNot(workspace.getOwner().getId(), workspaceDTO.getName(), id)) {
            return new ApiResponse("You already have a workspace with that name!", false);
        }
        workspace.setAvatar(workspaceDTO.getAvatarId() == null ? null : attachmentRepository.findById(workspaceDTO.getAvatarId()).orElseThrow(() -> new ResourceNotFoundException("avatar")));
        workspace.setColor(workspaceDTO.getColor());
        workspace.setName(workspaceDTO.getName());
        workspaceRepository.save(workspace);
        return new ApiResponse("Workspace successfully edited!", true);
    }


    @Override
    public ApiResponse changeOwnerWorkspace(Long id, UUID ownerId) {
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(id);
        if (!optionalWorkspace.isPresent())
            return new ApiResponse("There isn't workspace such an id!", false);
        Optional<User> optionalOwner = userRepository.findById(ownerId);
        if (!optionalOwner.isPresent())
            return new ApiResponse("There isn't user such an id!", false);
        Workspace workspace = optionalWorkspace.get();
        workspace.setOwner(optionalOwner.get());
        workspaceRepository.save(workspace);
        return new ApiResponse("Owner successfully changed!", true);
    }

    @Override
    public ApiResponse deleteWorkspace(Long id) {
        try {
            workspaceRepository.deleteById(id);
            return new ApiResponse("Successfully deleted!", true);
        } catch (Exception e) {
            return new ApiResponse("Error deleting!", false);
        }
    }

    @Override
    public ApiResponse addOrEditOrRemoveWorkspace(Long id, MemberDTO memberDTO) {
        if (memberDTO.getAddType().equals(AddType.ADD)) {
            WorkspaceUser workspaceUser = new WorkspaceUser(
                    workspaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id")),
                    userRepository.findById(memberDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("id")),
                    workspaceRoleRepository.findById(memberDTO.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("id")),
                    new Timestamp(System.currentTimeMillis()),null
            );
            workspaceUserRepository.save(workspaceUser);

            //TODO EMAILGA INVITE XABAR YUBORISH
        } else if (memberDTO.getAddType().equals(AddType.EDIT)) {
            WorkspaceUser workspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(id, memberDTO.getId()).orElseGet(WorkspaceUser::new);
            workspaceUser.setWorkspaceRole(workspaceRoleRepository.findById(memberDTO.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("id")));
            workspaceUserRepository.save(workspaceUser);
        } else if (memberDTO.getAddType().equals(AddType.REMOVE)) {
            workspaceUserRepository.deleteByWorkspaceIdAndUserId(id, memberDTO.getId());
        }
        return new ApiResponse("Successfully!", true);
    }

    @Override
    public ApiResponse joinToWorkspace(Long id, User user) {
        Optional<WorkspaceUser> optionalWorkspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(id, user.getId());
        if (optionalWorkspaceUser.isPresent()) {
            WorkspaceUser workspaceUser = optionalWorkspaceUser.get();
            workspaceUser.setDateJoined(new Timestamp(System.currentTimeMillis()));
            workspaceUserRepository.save(workspaceUser);
            return new ApiResponse("Successfully!", true);
        }
        return new ApiResponse("Error!", false);
    }

    @Override
    public List<MemberDTO> getWorkspaceMembersAndGuest(Long workspaceId) {
        List<WorkspaceUser> workspaceUsers = workspaceUserRepository.findAllByWorkspaceId(workspaceId);
        return workspaceUsers.stream().map(this::mapWorkspaceToMemberDTO).collect(Collectors.toList());

//         2-usul

//        List<MemberDTO> members=new ArrayList<>();
//        for (WorkspaceUser workspaceUser : workspaceUsers) {
//            MemberDTO memberDTO = mapWorkspaceToMemberDTO(workspaceUser);
//            members.add(memberDTO);
//        }
//        return members;
    }


    @Override
    public List<WorkspaceDTO> getMyWorkspaces(User user) {
        List<WorkspaceUser> workspaceUsers = workspaceUserRepository.findAllByUserId(user.getId());
        return workspaceUsers.stream().map(workspaceUser -> mapWorkspaceToWorkspaceDTO(workspaceUser.getWorkspace())).collect(Collectors.toList());
    }

    @Override
    public ApiResponse addOrRemovePermissionToRole(WorkspaceRoleDto workspaceRoleDto) {
        WorkspaceRole workspaceRole = workspaceRoleRepository.findById(workspaceRoleDto.getId()).orElseThrow(() -> new ResourceNotFoundException("workspaceRole"));
        Optional<WorkspacePermission> optionalWorkspacePermission = workspacePermissionRepository.findByWorkspaceRoleIdAndPermissionName(workspaceRole.getId(),workspaceRoleDto.getWorkspacePermissionName());
        if (workspaceRoleDto.getAddType().equals(AddType.ADD)){
            if (optionalWorkspacePermission.isPresent())
                return new ApiResponse("Already exist!",false);
            WorkspacePermission workspacePermission=new WorkspacePermission(workspaceRole,workspaceRoleDto.getWorkspacePermissionName());
            workspacePermissionRepository.save(workspacePermission);
            return new ApiResponse("Successfully added!",true);
        }else if (workspaceRoleDto.getAddType().equals(AddType.REMOVE)){
            if (optionalWorkspacePermission.isPresent()) {
                workspacePermissionRepository.delete(optionalWorkspacePermission.get());
                return new ApiResponse("Successfully deleted!", true);
            }
            return new ApiResponse("Object not found!", true);
        }
        return new ApiResponse("Such an add type does not exist!",false);
    }

    public WorkspaceDTO mapWorkspaceToWorkspaceDTO(Workspace workspace) {
        WorkspaceDTO workspaceDTO = new WorkspaceDTO();
        workspaceDTO.setId(workspace.getId());
        workspaceDTO.setInitialLetter(workspace.getInitialLetter());
        workspaceDTO.setName(workspace.getName());
        workspaceDTO.setAvatarId(workspace.getAvatar()==null?null:workspace.getAvatar().getId());
        workspaceDTO.setColor(workspace.getColor());
        return workspaceDTO;
    }

    public MemberDTO mapWorkspaceToMemberDTO(WorkspaceUser workspaceUser) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(workspaceUser.getUser().getId());
        memberDTO.setFullName(workspaceUser.getUser().getFullName());
        memberDTO.setEmail(workspaceUser.getUser().getEmail());
        memberDTO.setRoleName(workspaceUser.getWorkspaceRole().getName());
        memberDTO.setLastActive(workspaceUser.getUser().getLastActiveTime());
        return memberDTO;
    }
}
