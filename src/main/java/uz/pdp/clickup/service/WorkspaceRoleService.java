package uz.pdp.clickup.service;

import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.EditWorkspacePermissionDto;
import uz.pdp.clickup.payload.WorkspaceRoleDto;

public interface WorkspaceRoleService {

    ApiResponse addRole(WorkspaceRoleDto workspaceRoleDto);

    ApiResponse addPermissionToRole(EditWorkspacePermissionDto editWorkspacePermissionDto);

    ApiResponse removePermissionToRole(EditWorkspacePermissionDto editWorkspacePermissionDto);
}
