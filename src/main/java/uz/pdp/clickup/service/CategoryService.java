package uz.pdp.clickup.service;

import uz.pdp.clickup.entity.Category;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.CategoryDto;

import java.util.UUID;

public interface CategoryService {

    Category get(UUID id);

    ApiResponse create(CategoryDto dto);

    ApiResponse edit(UUID id, CategoryDto dto);

    ApiResponse delete(UUID id);
}
