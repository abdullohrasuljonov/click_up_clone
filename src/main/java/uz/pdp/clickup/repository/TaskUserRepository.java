package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.TaskUser;

import java.util.UUID;

public interface TaskUserRepository extends JpaRepository<TaskUser, UUID> {

    boolean existsByUserIdAndTaskId(UUID userId, UUID taskId);

    void deleteByUserIdAndTaskId(UUID userId, UUID taskId);
}
