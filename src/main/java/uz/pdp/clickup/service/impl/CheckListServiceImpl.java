package uz.pdp.clickup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.CheckList;
import uz.pdp.clickup.entity.Task;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.ChecklistDto;
import uz.pdp.clickup.repository.ChecklistRepository;
import uz.pdp.clickup.repository.TaskRepository;
import uz.pdp.clickup.service.CheckListService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CheckListServiceImpl implements CheckListService {

    @Autowired
    ChecklistRepository checklistRepository;

    @Autowired
    TaskRepository taskRepository;

    @Override
    public List<CheckList> getChecklistByTask(UUID taskId) {
        if (!taskRepository.existsById(taskId)) {
            return null;
        }
        return checklistRepository.findAllByTaskId(taskId);
    }

    @Override
    public ApiResponse create(ChecklistDto dto) {
        Optional<Task> optionalTask = taskRepository.findById(dto.getTaskId());
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found!", false);
        }
        CheckList checkList = new CheckList(dto.getName(), optionalTask.get());
        checklistRepository.save(checkList);
        return new ApiResponse("Successfully created!", true);
    }

    @Override
    public ApiResponse edit(UUID id, String name) {
        Optional<CheckList> optionalCheckList = checklistRepository.findById(id);
        if (!optionalCheckList.isPresent()) {
            return new ApiResponse("Checklist not found!", false);
        }
        CheckList checkList = optionalCheckList.get();
        checkList.setName(name);
        checklistRepository.save(checkList);
        return new ApiResponse("Successfully updated!", true);
    }

    @Override
    public ApiResponse delete(UUID id) {
        if (!checklistRepository.existsById(id)){
            return new ApiResponse("Checklist not found!", false);
        }
        checklistRepository.deleteById(id);
        return new ApiResponse("Successfully deleted", true);
    }
}
