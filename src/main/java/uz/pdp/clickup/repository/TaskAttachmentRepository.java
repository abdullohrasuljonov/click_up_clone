package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.TaskAttachment;

import java.util.UUID;

public interface TaskAttachmentRepository extends JpaRepository<TaskAttachment, UUID> {

    void deleteByAttachmentIdAndTaskId(UUID id, UUID id1);
}
