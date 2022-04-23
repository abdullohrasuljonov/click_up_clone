package uz.pdp.clickup.service;

import uz.pdp.clickup.entity.User;
import uz.pdp.clickup.entity.Workspace;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.MemberDTO;
import uz.pdp.clickup.payload.WorkspaceDTO;
import uz.pdp.clickup.payload.WorkspaceRoleDto;

import java.util.List;
import java.util.UUID;

public interface WorkspaceService {

    ApiResponse addWorkspace(WorkspaceDTO workspaceDTO, User user);

    ApiResponse editWorkspace(WorkspaceDTO workspaceDTO,Long id);

    ApiResponse changeOwnerWorkspace(Long id, UUID ownerId);

    ApiResponse deleteWorkspace(Long id);

    ApiResponse addOrEditOrRemoveWorkspace(Long id, MemberDTO memberDTO);

    ApiResponse joinToWorkspace(Long id, User user);

    List<MemberDTO> getWorkspaceMembersAndGuest(Long workspaceId);

    List<WorkspaceDTO> getMyWorkspaces(User user);

    ApiResponse addOrRemovePermissionToRole(WorkspaceRoleDto workspaceRoleDto);

}
