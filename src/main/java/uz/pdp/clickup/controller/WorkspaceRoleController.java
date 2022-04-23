package uz.pdp.clickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.EditWorkspacePermissionDto;
import uz.pdp.clickup.payload.WorkspaceRoleDto;
import uz.pdp.clickup.service.WorkspaceRoleService;

@RestController
@RequestMapping("/api/workspace/role")
public class WorkspaceRoleController {

    @Autowired
    WorkspaceRoleService workspaceRoleService;

    @PostMapping()
    public HttpEntity<?> addRole(@RequestBody WorkspaceRoleDto workspaceRoleDto) {
        ApiResponse apiResponse = workspaceRoleService.addRole(workspaceRoleDto);
        return ResponseEntity.ok(apiResponse);
    }


    @PutMapping("/add/permission")
    public HttpEntity<?> addPermissionToRole(@RequestBody EditWorkspacePermissionDto dto) {
        ApiResponse apiResponse = workspaceRoleService.addPermissionToRole(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/remove/permission")
    public HttpEntity<?> removePermissionToRole(@RequestBody EditWorkspacePermissionDto dto) {
        ApiResponse apiResponse = workspaceRoleService.removePermissionToRole(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
