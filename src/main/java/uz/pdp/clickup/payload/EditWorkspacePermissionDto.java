package uz.pdp.clickup.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
public class EditWorkspacePermissionDto {

    @NotNull
    private Long workspaceId;

    @NotNull
    private UUID roleId;

    List<String> permissions;
}
