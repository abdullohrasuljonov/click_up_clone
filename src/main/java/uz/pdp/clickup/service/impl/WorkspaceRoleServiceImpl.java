package uz.pdp.clickup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.Workspace;
import uz.pdp.clickup.entity.WorkspacePermission;
import uz.pdp.clickup.entity.WorkspaceRole;
import uz.pdp.clickup.entity.enums.WorkspacePermissionName;
import uz.pdp.clickup.entity.enums.WorkspaceRoleName;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.EditWorkspacePermissionDto;
import uz.pdp.clickup.payload.WorkspaceRoleDto;
import uz.pdp.clickup.repository.WorkspacePermissionRepository;
import uz.pdp.clickup.repository.WorkspaceRepository;
import uz.pdp.clickup.repository.WorkspaceRoleRepository;
import uz.pdp.clickup.service.WorkspaceRoleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkspaceRoleServiceImpl implements WorkspaceRoleService {

    @Autowired
    WorkspaceRoleRepository workspaceRoleRepository;
    @Autowired
    WorkspaceRepository workspaceRepository;
    @Autowired
    WorkspacePermissionRepository workspacePermissionRepository;
    @Override
    public ApiResponse addRole(WorkspaceRoleDto workspaceRoleDto) {
        if (workspaceRoleRepository.existsByNameAndWorkspaceId(workspaceRoleDto.getName(), workspaceRoleDto.getWorkspaceId())) {
            return new ApiResponse("Role has already created", false);
        }
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(workspaceRoleDto.getWorkspaceId());
        if (!optionalWorkspace.isPresent()) {
            return new ApiResponse("Workspace not found!", false);
        }
        Workspace workspace = optionalWorkspace.get();
        Optional<WorkspaceRole> optionalWorkspaceRole = workspaceRoleRepository.findById(workspaceRoleDto.getExtendsRoleId());
        if (!optionalWorkspaceRole.isPresent()) {
            return new ApiResponse("Workspace role not found!", false);
        }
        WorkspaceRole extendsRole = optionalWorkspaceRole.get();
        WorkspaceRole workspaceRole = new WorkspaceRole(
                workspace,
                workspaceRoleDto.getName(),
                WorkspaceRoleName.valueOf(extendsRole.getName())
        );
        WorkspaceRole savedWorkspaceRole = workspaceRoleRepository.save(workspaceRole);

        List<WorkspacePermission> permissions = new ArrayList<>();
        for (WorkspacePermission permission : workspacePermissionRepository.findAllByWorkspaceRole_NameAndWorkspaceRole_WorkspaceId(extendsRole.getName(),workspaceRoleDto.getWorkspaceId())) {
            WorkspacePermission workspacePermission = new WorkspacePermission(savedWorkspaceRole, permission.getPermissionName());
            permissions.add(workspacePermission);
        }
        workspacePermissionRepository.saveAll(permissions);
        return new ApiResponse("Successfully created!", true);
    }


    @Override
    public ApiResponse addPermissionToRole(EditWorkspacePermissionDto dto) {
        if (!workspaceRepository.existsById(dto.getWorkspaceId())) {
            return new ApiResponse("Workspace not found!", false);
        }
        Optional<WorkspaceRole> optionalRole = workspaceRoleRepository.findByIdAndWorkspaceId(dto.getRoleId(), dto.getWorkspaceId());
        if (!optionalRole.isPresent()) {
            return new ApiResponse("Role not found!", false);
        }
        WorkspaceRole workspaceRole = optionalRole.get();
        List<WorkspacePermission> permissions = workspacePermissionRepository.findAllByRolePermission(dto.getWorkspaceId(), workspaceRole.getName());
        List<WorkspacePermission> newPermission = new ArrayList<>(permissions);
        for (String dtoPermission : dto.getPermissions()) {
            boolean has = false;
            for (WorkspacePermission permission : permissions) {
                if (permission.getPermissionName().equals(WorkspacePermissionName.valueOf(dtoPermission))) {
                    has = true;
                    break;
                }
            }
            if (!has){
                newPermission.add(new WorkspacePermission(
                        workspaceRole,
                        WorkspacePermissionName.valueOf(dtoPermission)
                ));
            }
        }
        workspacePermissionRepository.saveAll(newPermission);
        return new ApiResponse("Successfully edited!", true);
    }

    @Override
    public ApiResponse removePermissionToRole(EditWorkspacePermissionDto dto) {
        if (!workspaceRepository.existsById(dto.getWorkspaceId())) {
            return new ApiResponse("Workspace not found!", false);
        }
        Optional<WorkspaceRole> optionalRole = workspaceRoleRepository.findByIdAndWorkspaceId(dto.getRoleId(), dto.getWorkspaceId());
        if (!optionalRole.isPresent()) {
            return new ApiResponse("Role not found!", false);
        }
        WorkspaceRole workspaceRole = optionalRole.get();
        List<WorkspacePermission> permissions = workspacePermissionRepository.findAllByRolePermission(dto.getWorkspaceId(), workspaceRole.getName());
        List<WorkspacePermission> deletedPermissions = new ArrayList<>();
        for (String dtoPermission : dto.getPermissions()) {
            for (WorkspacePermission permission : permissions) {
                if (permission.getPermissionName().equals(WorkspacePermissionName.valueOf(dtoPermission))) {
                    deletedPermissions.add(permission);
                }
            }
        }
        workspacePermissionRepository.deleteAll(deletedPermissions);
        return new ApiResponse("Successfully edited!", true);
    }

}
