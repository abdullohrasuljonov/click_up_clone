package uz.pdp.clickup.payload;

import lombok.Data;
import uz.pdp.clickup.entity.enums.AccessType;

import javax.validation.constraints.*;
import java.util.List;
import java.util.UUID;

@Data
public class SpaceDto {

    @NotBlank
    private String name;

    @NotNull
    private String color;

    @NotNull
    private Long workspaceId;

    private UUID avatarId;

    private Long iconId;

    private AccessType accessType; // public, private

    private List<UUID> members;
}
