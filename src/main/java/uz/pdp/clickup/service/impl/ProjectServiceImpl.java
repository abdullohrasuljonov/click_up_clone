package uz.pdp.clickup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.*;
import uz.pdp.clickup.entity.enums.AccessType;
import uz.pdp.clickup.entity.enums.StatusType;
import uz.pdp.clickup.entity.enums.WorkspacePermissionName;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.AttachMemberPermissionDto;
import uz.pdp.clickup.payload.ProjectDto;
import uz.pdp.clickup.repository.*;
import uz.pdp.clickup.service.ProjectService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    SpaceUserRepository spaceUserRepository;

    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    ProjectUserRepository projectUserRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StatusRepository statusRepository;

    @Override
    public ApiResponse create(ProjectDto dto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (projectRepository.existsByNameAndSpaceId(dto.getName(), dto.getSpaceId())) {
            return new ApiResponse("Project has already exist!", false);
        }
        Optional<Space> optionalSpace = spaceRepository.findById(dto.getSpaceId());
        if (!optionalSpace.isPresent()) {
            return new ApiResponse("Space not found!", false);
        }
        Space space = optionalSpace.get();
        Project project = new Project();
        List<Category> categories = new ArrayList<>();
        for (String list : dto.getLists()) {
            Category category = new Category(list, project);
            categories.add(category);
        }
        project.setColor(dto.getColor());
        project.setCategories(categories);
        project.setAccessType(dto.getAccessType());
        project.setSpace(space);
        project.setName(dto.getName());
        Project savedProject = projectRepository.save(project);
        for (Category category : savedProject.getCategories()) {
            statusRepository.save(new Status(
                    "TO DO",
                    "gray",
                    space,
                    project,
                    category,
                    StatusType.OPEN
            ));
            statusRepository.save(new Status(
                    "Completed",
                    "green",
                    space,
                    project,
                    category,
                    StatusType.CLOSED
            ));
        }
        if (dto.getAccessType().equals(AccessType.PUBLIC)) {
            for (SpaceUser spaceUser : spaceUserRepository.findAllBySpaceId(dto.getSpaceId())) {
                for (WorkspacePermissionName value : WorkspacePermissionName.values()) {
                    projectUserRepository.save(new ProjectUser(spaceUser.getMember(), savedProject, value));
                }
            }
        } else {
            for (WorkspacePermissionName value : WorkspacePermissionName.values()) {
                projectUserRepository.save(new ProjectUser(user, savedProject, value));
            }
        }
        return new ApiResponse("Successfully created!", true);
    }


    @Override
    public ApiResponse attachMember(UUID id, AttachMemberPermissionDto dto) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (!optionalProject.isPresent()) {
            return new ApiResponse("Project not found!", false);
        }
        Project project = optionalProject.get();
        List<ProjectUser> projectUsers = new ArrayList<>();
        dto.getMemberPermission().forEach((uuid, permissionNames) -> {
                    if (spaceUserRepository.existsByMemberIdAndSpaceId(uuid, project.getSpace().getId())) {
                        for (WorkspacePermissionName permissionName : permissionNames) {
                            projectUsers.add(new ProjectUser(userRepository.getById(uuid), project, permissionName));
                        }
                    }
                }
        );
        projectUserRepository.saveAll(projectUsers);
        return new ApiResponse("OK", true);
    }


    @Override
    public ApiResponse deleteMember(UUID id, UUID memberId) {
        if (projectUserRepository.existsByProjectIdAndMemberId(id, memberId)) {
            projectUserRepository.deleteAllByProjectIdAndMemberId(id, memberId);
        }
        return new ApiResponse("Successfully deleted!", true);
    }
}
