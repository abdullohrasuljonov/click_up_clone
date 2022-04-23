package uz.pdp.clickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.entity.Space;
import uz.pdp.clickup.entity.User;
import uz.pdp.clickup.entity.View;
import uz.pdp.clickup.payload.*;
import uz.pdp.clickup.security.CurrentUser;
import uz.pdp.clickup.service.SpaceService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/space")
public class SpaceController {

    @Autowired
    SpaceService spaceService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id) {
        Space space = spaceService.get(id);
        return ResponseEntity.ok(space);
    }

    @GetMapping("/workspace/{id}")
    public ResponseEntity<?> getWorkspaceId(@PathVariable(value = "id") Long workspaceId) {
        List<Space> spaces = spaceService.getWorkspaceId(workspaceId);
        return ResponseEntity.ok(spaces);
    }

    @PostMapping
    public ResponseEntity<?> addSpace(@Valid @RequestBody SpaceDto dto, @CurrentUser User user) {
        ApiResponse apiResponse = spaceService.addSpace(dto, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<?> editSpace(@Valid @RequestBody SpaceDto dto, @PathVariable UUID id) {
        ApiResponse apiResponse = spaceService.editSpace(dto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}/attach/members")
    public ResponseEntity<?> attachMembers(@Valid @RequestBody AttachMemberDto dto, @PathVariable UUID id) {
        ApiResponse apiResponse = spaceService.attachMembers(dto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/views/{id}")
    public ResponseEntity<?> getViewBySpace(@PathVariable UUID id) {
        List<View> viewBySpaces = spaceService.getViewBySpace(id);
        return ResponseEntity.ok(viewBySpaces);
    }

    @PutMapping("/{id}/attach/views")
    public ResponseEntity<?> attachViews(@Valid @RequestBody AttachViewsDto dto, @PathVariable UUID id) {
        ApiResponse apiResponse = spaceService.attachViews(dto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}/attach/click/apps")
    public ResponseEntity<?> attachClickApp(@Valid @RequestBody AttachClickAppDto dto, @PathVariable UUID id) {
        ApiResponse apiResponse = spaceService.attachClickApp(dto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}/detach/members")
    public ResponseEntity<?> detachMembers(@Valid @RequestBody AttachMemberDto dto, @PathVariable UUID id) {
        ApiResponse apiResponse = spaceService.detachMembers(dto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}/detach/views")
    public ResponseEntity<?> detachViews(@Valid @RequestBody AttachViewsDto dto, @PathVariable UUID id) {
        ApiResponse apiResponse = spaceService.detachViews(dto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}/detach/click/apps")
    public ResponseEntity<?> detachClickApp(@Valid @RequestBody AttachClickAppDto dto, @PathVariable UUID id) {
        ApiResponse apiResponse = spaceService.detachClickApp(dto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        ApiResponse apiResponse = spaceService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
