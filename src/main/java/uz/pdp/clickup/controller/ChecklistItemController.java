package uz.pdp.clickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.entity.CheckListItem;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.ChecklistItemDto;
import uz.pdp.clickup.payload.ItemUserDto;
import uz.pdp.clickup.service.CheckListItemService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/checklist/item")
public class ChecklistItemController {

    @Autowired
    CheckListItemService checkListItemService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getByChecklist(@PathVariable(value = "id") UUID checklistId) {
        List<CheckListItem> checkList = checkListItemService.getByChecklist(checklistId);
        return ResponseEntity.ok(checkList);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ChecklistItemDto dto) {
        ApiResponse apiResponse = checkListItemService.create(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/assign/user")
    public ResponseEntity<?> assign(@Valid @RequestBody ItemUserDto dto) {
        ApiResponse apiResponse = checkListItemService.assign(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/remove/user")
    public ResponseEntity<?> removeUser(@Valid @RequestBody ItemUserDto dto) {
        ApiResponse apiResponse = checkListItemService.removeUser(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id, @RequestParam String name) {
        ApiResponse apiResponse = checkListItemService.edit(id, name);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/resolve/{id}")
    public ResponseEntity<?> resolve(@PathVariable UUID id) {
        ApiResponse apiResponse = checkListItemService.resolve(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        ApiResponse apiResponse = checkListItemService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


}
