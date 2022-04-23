package uz.pdp.clickup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.*;
import uz.pdp.clickup.entity.enums.StatusType;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.CategoryDto;
import uz.pdp.clickup.repository.CategoryRepository;
import uz.pdp.clickup.repository.ProjectRepository;
import uz.pdp.clickup.repository.SpaceRepository;
import uz.pdp.clickup.repository.StatusRepository;
import uz.pdp.clickup.service.CategoryService;

import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    SpaceRepository spaceRepository;
    @Autowired
    StatusRepository statusRepository;

    @Override
    public Category get(UUID id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.orElse(null);
    }

    @Override
    public ApiResponse create(CategoryDto dto) {
        if (categoryRepository.existsByNameAndProjectId(dto.getName(), dto.getProjectId())) {
            return new ApiResponse("Category has already exist!", false);
        }
        Optional<Project> optionalProject = projectRepository.findById(dto.getProjectId());
        if (!optionalProject.isPresent()) {
            return new ApiResponse("Project not found!", false);
        }
        Optional<Space> optionalSpace = spaceRepository.findById(dto.getSpaceId());
        if (!optionalSpace.isPresent()) {
            return new ApiResponse("Space not found!", false);
        }
        Space space = optionalSpace.get();
        Project project = optionalProject.get();
        Category category = new Category(dto.getName(), project);
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
        return new ApiResponse("Successfully created!", true);
    }

    @Override
    public ApiResponse edit(UUID id, CategoryDto dto) {
        if (categoryRepository.existsByNameAndProjectIdAndIdNot(dto.getName(), dto.getProjectId(), id)) {
            return new ApiResponse("Category has already exist!", false);
        }
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            return new ApiResponse("Category not found!", false);
        }
        Optional<Project> optionalProject = projectRepository.findById(dto.getProjectId());
        if (!optionalProject.isPresent()) {
            return new ApiResponse("Project not found!", false);
        }
        Project project = optionalProject.get();
        Category category = optionalCategory.get();
        category.setName(dto.getName());
        category.setProject(project);
        categoryRepository.save(category);
        return new ApiResponse("Successfully updated!", true);
    }

    @Override
    public ApiResponse delete(UUID id) {
        try {
            categoryRepository.deleteById(id);
            return new ApiResponse("Successfully deleted!",true);
        }catch (Exception e){
            return new ApiResponse("Deleting error!",false);
        }
    }

}
