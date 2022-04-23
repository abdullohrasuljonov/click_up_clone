package uz.pdp.clickup.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class TaskTagDto {

    @NotNull
    private String name;

    private String color;

    @NotNull
    private Long workspaceId;

    @NotNull
    private UUID taskId;
}
