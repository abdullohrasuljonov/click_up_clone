package uz.pdp.clickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.entity.Status;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.StatusDTO;
import uz.pdp.clickup.payload.StatusEditDTO;
import uz.pdp.clickup.service.StatusService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    @Autowired
    StatusService statusService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id) {
        Status status = statusService.get(id);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getByCategory(@PathVariable(value = "id") UUID categoryId) {
       List<Status> statusList = statusService.getByCategory(categoryId);
        return ResponseEntity.ok(statusList);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody StatusDTO dto) {
        ApiResponse apiResponse = statusService.create(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id, @Valid @RequestBody StatusEditDTO dto) {
        ApiResponse apiResponse = statusService.edit(id, dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        ApiResponse apiResponse = statusService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
