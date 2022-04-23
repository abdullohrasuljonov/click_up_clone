package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.TaskTag;

import java.util.UUID;

public interface TaskTagRepository extends JpaRepository<TaskTag, UUID> {

    boolean existsByTagIdAndTaskId(Long tagId, UUID taskId);

    void deleteAllByTagIdAndTaskId(Long tag_id, UUID task_id);
}
