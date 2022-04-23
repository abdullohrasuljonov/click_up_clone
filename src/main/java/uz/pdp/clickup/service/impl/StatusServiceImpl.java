package uz.pdp.clickup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.*;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.StatusDTO;
import uz.pdp.clickup.payload.StatusEditDTO;
import uz.pdp.clickup.repository.CategoryRepository;
import uz.pdp.clickup.repository.ProjectRepository;
import uz.pdp.clickup.repository.SpaceRepository;
import uz.pdp.clickup.repository.StatusRepository;
import uz.pdp.clickup.service.StatusService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    StatusRepository statusRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    SpaceRepository spaceRepository;

    @Override
    public Status get(UUID id) {
        Optional<Status> optionalStatus = statusRepository.findById(id);
        return optionalStatus.orElse(null);
    }

    @Override
    public List<Status> getByCategory(UUID categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (!optionalCategory.isPresent())
            return null;
        return statusRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public ApiResponse create(StatusDTO dto) {
        boolean exists = statusRepository.existsByNameAndSpaceId(dto.getName(), dto.getSpaceId());
        if (exists)
            return new ApiResponse("Status already exist such space!", false);
        boolean exists1 = spaceRepository.existsById(dto.getSpaceId());
        Optional<Space> optionalSpace = spaceRepository.findById(dto.getSpaceId());
        if (!optionalSpace.isPresent()) {
            return new ApiResponse("Space not found!", false);
        }
        Optional<Project> optionalProject = projectRepository.findById(dto.getProjectId());
        if (!optionalProject.isPresent()) {
            return new ApiResponse("Project not found!", false);
        }
        Optional<Category> optionalCategory = categoryRepository.findById(dto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return new ApiResponse("Category not found!", false);
        }
        Status status = new Status(dto.getName(), dto.getColor(),
                optionalSpace.get(), optionalProject.get(),
                optionalCategory.get(), dto.getStatusType());
        statusRepository.save(status);
        return new ApiResponse("Successfully created!",true);
    }

    @Override
    public ApiResponse edit(UUID id, StatusEditDTO dto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Status> optionalStatus = statusRepository.findById(id);
        if (!optionalStatus.isPresent()) {
            return new ApiResponse("Status not found!", false);
        }
        Status status = optionalStatus.get();
        if (statusRepository.existsByNameAndSpaceIdAndIdNot(dto.getName(), status.getSpace().getId(), id)) {
            return new ApiResponse("Status has already exist!", false);
        }
        if (status.getCreatedBy().getId() == user.getId()) {
            status.setName(dto.getName());
            status.setColor(dto.getColor());
            status.setStatusType(dto.getStatusType());
            statusRepository.save(status);
            return new ApiResponse("Successfully updated!", true);
        }
        return new ApiResponse("Error in editing!",false);
    }

    @Override
    public ApiResponse delete(UUID id) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<Status> optionalStatus = statusRepository.findById(id);
            if (!optionalStatus.isPresent()) return new ApiResponse("Status not found!", false);
            Status status = optionalStatus.get();
            if (status.getCreatedBy().getId() == user.getId()) {
                statusRepository.deleteById(id);
                return new ApiResponse("Successfully deleted!", true);
            }
            return new ApiResponse("Error in deleting!", false);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting!", false);
        }
    }
}
