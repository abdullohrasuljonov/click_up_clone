package uz.pdp.clickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.entity.User;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.MemberDTO;
import uz.pdp.clickup.payload.WorkspaceDTO;
import uz.pdp.clickup.payload.WorkspaceRoleDto;
import uz.pdp.clickup.security.CurrentUser;
import uz.pdp.clickup.service.WorkspaceService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/workspace")
public class WorkspaceController {
    @Autowired
    WorkspaceService workspaceService;

    @PostMapping
    public HttpEntity<?> addWorkspace(@Valid @RequestBody WorkspaceDTO workspaceDTO, @CurrentUser User user) {
        ApiResponse apiResponse = workspaceService.addWorkspace(workspaceDTO, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /**
     * NAME, COLOR, AVATAR O'ZGARAISHI MUMKIN
     *
     * @param id
     * @param workspaceDTO
     * @return
     */
    @PutMapping("/{id}")
    public HttpEntity<?> editWorkspace(@PathVariable Long id, @RequestBody WorkspaceDTO workspaceDTO) {
        ApiResponse apiResponse = workspaceService.editWorkspace(workspaceDTO, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /**
     * @param id
     * @param ownerId
     * @return
     */
    @PutMapping("/changeOwner/{id}")
    public HttpEntity<?> changeOwnerWorkspace(@PathVariable Long id,
                                              @RequestParam UUID ownerId) {
        ApiResponse apiResponse = workspaceService.changeOwnerWorkspace(id, ownerId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * ISHXONANI O'CHIRISH
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteWorkspace(@PathVariable Long id) {
        ApiResponse apiResponse = workspaceService.deleteWorkspace(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/addOrEditOrRemove/{id}")
    public HttpEntity<?> addOrEditOrRemoveWorkspace(@PathVariable Long id,
                                                    @RequestBody MemberDTO memberDTO) {
        ApiResponse apiResponse = workspaceService.addOrEditOrRemoveWorkspace(id, memberDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/join")
    public HttpEntity<?> joinToWorkspace(@RequestParam Long id,
                                         @CurrentUser User user) {
        ApiResponse apiResponse = workspaceService.joinToWorkspace(id, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/member/{workspaceId}")
    public HttpEntity<?> getWorkspaceMembersAndGuest(@PathVariable Long workspaceId) {
        List<MemberDTO> workspaceMembers = workspaceService.getWorkspaceMembersAndGuest(workspaceId);
        return ResponseEntity.ok(workspaceMembers);
    }


    @GetMapping()
    public HttpEntity<?> getMyWorkspaces(@CurrentUser User user) {
        List<WorkspaceDTO> all = workspaceService.getMyWorkspaces(user);
        return ResponseEntity.ok(all);
    }

    @PostMapping("/addOrRemovePermission")
    public HttpEntity<?> addOrRemovePermissionToRole(@RequestBody WorkspaceRoleDto workspaceRoleDto) {
        ApiResponse apiResponse = workspaceService.addOrRemovePermissionToRole(workspaceRoleDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
