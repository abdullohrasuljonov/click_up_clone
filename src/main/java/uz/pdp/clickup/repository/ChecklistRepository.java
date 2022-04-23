package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.CheckList;

import java.util.List;
import java.util.UUID;

public interface ChecklistRepository extends JpaRepository<CheckList, UUID> {

    List<CheckList> findAllByTaskId(UUID taskId);
}
