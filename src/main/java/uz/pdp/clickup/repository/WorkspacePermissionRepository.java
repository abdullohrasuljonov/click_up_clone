package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.clickup.entity.WorkspacePermission;
import uz.pdp.clickup.entity.enums.WorkspacePermissionName;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkspacePermissionRepository extends JpaRepository<WorkspacePermission, UUID> {

    Optional<WorkspacePermission> findByWorkspaceRoleIdAndPermissionName(UUID workspaceRole_id, WorkspacePermissionName permissionName);

    List<WorkspacePermission> findAllByWorkspaceRole_NameAndWorkspaceRole_WorkspaceId(String workspaceRole_name, Long workspaceRole_workspace_id);

    @Query("select w from WorkspacePermission w where w.workspaceRole.workspace.id = ?1 and w.workspaceRole.name = ?2")
    List<WorkspacePermission> findAllByRolePermission(Long workspaceRole_workspace_id, String workspaceRole_name);}
