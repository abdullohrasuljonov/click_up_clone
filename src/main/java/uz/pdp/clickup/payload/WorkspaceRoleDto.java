package uz.pdp.clickup.payload;

import lombok.Data;
import uz.pdp.clickup.entity.enums.AddType;
import uz.pdp.clickup.entity.enums.WorkspacePermissionName;
import uz.pdp.clickup.entity.enums.WorkspaceRoleName;

import java.util.UUID;

@Data
public class WorkspaceRoleDto {

    private UUID id;

    private String name;

    private WorkspaceRoleName extendsRoleName;

    private WorkspacePermissionName workspacePermissionName;

    private AddType addType;

    private Long workspaceId;

    private UUID extendsRoleId;
}
