package uz.pdp.clickup.service;

import org.springframework.web.multipart.MultipartFile;
import uz.pdp.clickup.entity.Task;
import uz.pdp.clickup.payload.*;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    Task getId(UUID id);

    List<Task> getByCategoryId(UUID categoryId);

    ApiResponse create(TaskDTO taskDTO);

    ApiResponse createSubtask(SubtaskDto dto);

    ApiResponse changeStatus(UUID id, UUID statusId);

    ApiResponse changePriority(UUID id, Long priorityId);

    ApiResponse changeEstimate(UUID id, Long estimate);

    ApiResponse changeDescription(UUID id, String description);

    ApiResponse attachFile(UUID id, MultipartFile file);

    ApiResponse detachFile(UUID id, String fileName);

    ApiResponse dueDate(UUID id, DueDateDto dto);

    ApiResponse addTag(TaskTagDto dto);

    ApiResponse removeTag(Long tagId, UUID taskId);

    ApiResponse assignUser(UUID taskId, UUID userId);

    ApiResponse removeUser(UUID taskId, UUID userId);
}
