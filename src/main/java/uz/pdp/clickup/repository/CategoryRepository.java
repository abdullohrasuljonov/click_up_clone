package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.Category;
import uz.pdp.clickup.payload.CategoryDto;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    boolean existsByNameAndProjectId(String name, UUID projectId);

    boolean existsByNameAndProjectIdAndIdNot(String name, UUID projectId, UUID id);
}
