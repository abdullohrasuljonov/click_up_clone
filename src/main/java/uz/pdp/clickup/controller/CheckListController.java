package uz.pdp.clickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.entity.CheckList;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.ChecklistDto;
import uz.pdp.clickup.service.CheckListService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/checklist")
public class CheckListController {

    @Autowired
    CheckListService checkListService;

    @GetMapping("/task/{id}")
    public ResponseEntity<?> getChecklistByTask(@PathVariable(value = "id") UUID taskId) {
        List<CheckList> checklistByTask = checkListService.getChecklistByTask(taskId);
        return ResponseEntity.ok(checklistByTask);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ChecklistDto dto) {
        ApiResponse apiResponse = checkListService.create(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id, @RequestParam String name) {
        ApiResponse apiResponse = checkListService.edit(id, name);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        ApiResponse apiResponse = checkListService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
