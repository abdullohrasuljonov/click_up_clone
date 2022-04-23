package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.Status;

import java.util.List;
import java.util.UUID;

public interface StatusRepository extends JpaRepository<Status, UUID> {

    List<Status> findAllByCategoryId(UUID category_id);

    boolean existsByNameAndSpaceId(String name, UUID space_id);

    boolean existsByNameAndSpaceIdAndIdNot(String name, UUID id, UUID id1);
}
