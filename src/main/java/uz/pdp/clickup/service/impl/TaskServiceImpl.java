package uz.pdp.clickup.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.clickup.entity.*;
import uz.pdp.clickup.entity.enums.AddType;
import uz.pdp.clickup.payload.*;
import uz.pdp.clickup.repository.*;
import uz.pdp.clickup.service.TaskService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    PriorityRepository priorityRepository;

    @Autowired
    TaskHistoryRepository taskHistoryRepo;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    TaskAttachmentRepository taskAttachmentRepository;

    @Autowired
    WorkspaceRepository workspaceRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    TaskTagRepository taskTagRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskUserRepository taskUserRepository;

    @Autowired
    TaskHistoryRepository taskHistoryRepository;

    private final Path root = Paths.get("C:\\");

    @Override
    public Task getId(UUID id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.orElse(null);
    }

    @Override
    public List<Task> getByCategoryId(UUID categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (!optionalCategory.isPresent())
            return  null;
        return taskRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public ApiResponse create(TaskDTO taskDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Status> optionalStatus = statusRepository.findById(taskDTO.getStatusId());
        if (!optionalStatus.isPresent()) {
            return new ApiResponse("Status not found!", false);
        }
        Optional<Category> optionalCategory = categoryRepository.findById(taskDTO.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return new ApiResponse("Category not found!", false);
        }
        Optional<Priority> optionalPriority = priorityRepository.findById(taskDTO.getPriorityId());
        if (!optionalPriority.isPresent()) {
            return new ApiResponse("Priority not found!", false);
        }
        Status status = optionalStatus.get();
        Category category = optionalCategory.get();
        Priority priority = optionalPriority.get();
        Task task = new Task(taskDTO.getName(), status, category, priority, taskDTO.getStartDate(), taskDTO.isStartTimeHas(), taskDTO.isDueTimeHas(), taskDTO.getDueDate());
        Task savedTask = taskRepository.save(task);

        createTaskHistory(savedTask, null, null, null,
                user.getUsername() + " created task");

        TaskUser taskUser = new TaskUser(savedTask, user);
        taskUserRepository.save(taskUser);
        return new ApiResponse("Successfully created!", true);
    }


    @Override
    public ApiResponse createSubtask(SubtaskDto dto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Task> optionalTask = taskRepository.findById(dto.getParentId());
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Parent task not found!", false);
        }
        Optional<Priority> optionalPriority = priorityRepository.findById(dto.getPriorityId());
        if (!optionalPriority.isPresent()) {
            return new ApiResponse("Priority not found!", false);
        }
        Task task = optionalTask.get();
        Priority priority = optionalPriority.get();
        Task subtask = new Task(dto.getName(), priority, task, dto.getStartedDate(), dto.isStartTimeHas(), dto.isDueTimeHas(), dto.getDueDate(), dto.getEstimateTime(), dto.getActivateDate());
        taskRepository.save(subtask);
        createTaskHistory(task, "Subtask", null, null,
                user.getUsername() + " created subtask");
        return new ApiResponse("Successfully created!", true);
    }

    @Override
    public ApiResponse changeStatus(UUID id, UUID statusId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found!", false);
        }
        Optional<Status> optionalStatus = statusRepository.findById(statusId);
        if (!optionalStatus.isPresent()) {
            return new ApiResponse("Status not found!", false);
        }
        Status status = optionalStatus.get();
        Task task = optionalTask.get();
        createTaskHistory(task, "status", task.getStatus().getName(), status.getName(),
                user.getUsername() + " changed status from " + task.getStatus().getName() + " to " +
                        status.getName());
        task.setStatus(status);
        taskRepository.save(task);
        return new ApiResponse("Successfully updated!", true);
    }

    @Override
    public ApiResponse changePriority(UUID id, Long priorityId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Priority> optionalPriority = priorityRepository.findById(priorityId);
        if (!optionalPriority.isPresent()) {
            return new ApiResponse("Priority not found!", false);
        }
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found!", false);
        }
        Priority priority = optionalPriority.get();
        Task task = optionalTask.get();
        createTaskHistory(task, "Priority", task.getPriority().getName(), priority.getName(),
                user.getUsername() + " changed priority");
        task.setPriority(priority);
        taskRepository.save(task);
        return new ApiResponse("Successfully updated!", true);
    }

    @Override
    public ApiResponse changeEstimate(UUID id, Long estimate) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found!", false);
        }
        Task task = optionalTask.get();
        createTaskHistory(task, "Estimate", String.valueOf(task.getEstimateTime()), String.valueOf(estimate),
                user.getUsername() + " changed estimate time");
        task.setEstimateTime(estimate);
        taskRepository.save(task);
        return new ApiResponse("Successfully updated!", true);
    }

    @Override
    public ApiResponse changeDescription(UUID id, String description) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found!", false);
        }
        Task task = optionalTask.get();
        createTaskHistory(task, "description", task.getDescription(), description,
                user.getUsername() + " changed description");
        return new ApiResponse("Successfully updated!", true);
    }

    @Override
    public ApiResponse attachFile(UUID id, MultipartFile file) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found!", false);
        }
        Task task = optionalTask.get();
        String uniqueName = UUID.randomUUID().toString();

        try {
            Files.copy(file.getInputStream(), root.resolve(uniqueName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Attachment attachment = new Attachment();
        attachment.setName(uniqueName);
        attachment.setOriginalName(file.getOriginalFilename());
        attachment.setPath(root.toString());
        attachment.setContentType(file.getContentType());
        attachment.setSize(file.getSize());

        Attachment savedAttachment = attachmentRepository.save(attachment);
        TaskAttachment taskAttachment = new TaskAttachment(task, savedAttachment);
        taskAttachmentRepository.save(taskAttachment);
        createTaskHistory(task, "File", null, null,
                user.getUsername() + " attached file");

        return new ApiResponse("Attached!", true);
    }

    @Override
    public ApiResponse detachFile(UUID id, String fileName) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Attachment attachment = attachmentRepository.findByName(fileName);
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found", false);
        }
        Task task = optionalTask.get();
        taskAttachmentRepository.deleteByAttachmentIdAndTaskId(attachment.getId(), task.getId());
        createTaskHistory(task, "File", null, null,
                user.getUsername() + " detached file");

        return new ApiResponse("Detached!", true);
    }

    @Override
    public ApiResponse dueDate(UUID id, DueDateDto dto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Parent task not found!", false);
        }
        Task task = optionalTask.get();

        if (dto.getAddType().equals(AddType.REMOVE)) {
            task.setDueDate(null);
            task.setDueTimeHas(false);
            task.setStartedDate(null);
            task.setStartTimeHas(false);
        } else {
            task.setDueDate(dto.getDueDate());
            task.setDueTimeHas(dto.isDueTimeHas());
            task.setStartedDate(dto.getStartedDate());
            task.setStartTimeHas(dto.isStartTimeHas());
        }
        taskRepository.save(task);
        createTaskHistory(task, "Due date", null, null,
                user.getUsername() + " changed due date");
        return new ApiResponse("Successfully updated!", true);
    }

    @Override
    public ApiResponse addTag(TaskTagDto dto) {
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(dto.getWorkspaceId());
        if (!optionalWorkspace.isPresent()) {
            return new ApiResponse("Workspace not found!", false);
        }
        Optional<Task> optionalTask = taskRepository.findById(dto.getTaskId());
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found!", false);
        }
        Workspace workspace = optionalWorkspace.get();
        Task task = optionalTask.get();
        Tag tag = new Tag(dto.getName(), dto.getColor(), workspace);
        Tag savedTag = tagRepository.save(tag);
        TaskTag taskTag = new TaskTag(task, savedTag);
        taskTagRepository.save(taskTag);
        createTaskHistory(task, "Tag", null, tag.getName(), "Tag added!");
        return new ApiResponse("Successfully created!", true);
    }

    @Override
    public ApiResponse removeTag(Long tagId, UUID taskId) {
        if (taskTagRepository.existsByTagIdAndTaskId(tagId, taskId)) {
            taskTagRepository.deleteAllByTagIdAndTaskId(tagId, taskId);
            createTaskHistory(taskRepository.getById(taskId), "Tag", tagRepository.getById(tagId).getName(), null, "Tag removed");
            return new ApiResponse("Removed!", true);
        }
        return new ApiResponse("Not found!", false);
    }

    @Override
    public ApiResponse assignUser(UUID taskId, UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            return new ApiResponse("User not found!", false);
        }
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found!", false);
        }
        TaskUser taskUser = new TaskUser(optionalTask.get(), optionalUser.get());
        taskUserRepository.save(taskUser);
        createTaskHistory(optionalTask.get(), "User", null, optionalUser.get().getUsername(),
                "User assigned!");
        return new ApiResponse("Successfully assigned!", true);
    }

    @Override
    public ApiResponse removeUser(UUID taskId, UUID userId) {
        if (taskUserRepository.existsByUserIdAndTaskId(userId, taskId)) {
            taskUserRepository.deleteByUserIdAndTaskId(userId, taskId);
            createTaskHistory(taskRepository.getById(taskId), "User", userRepository.getById(userId).getUsername(),
                    null,"User removed!");
            return new ApiResponse("Removed!", true);
        }
        return new ApiResponse("Not found!", false);
    }


    private void createTaskHistory(Task task, String changeField, String before, String after, String data) {
        TaskHistory taskHistory = new TaskHistory(task, changeField, before, after, data);
        taskHistoryRepository.save(taskHistory);
    }
}
