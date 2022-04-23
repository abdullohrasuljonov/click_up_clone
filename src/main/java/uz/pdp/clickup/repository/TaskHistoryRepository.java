package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.TaskHistory;

import java.util.UUID;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, UUID> {
}
