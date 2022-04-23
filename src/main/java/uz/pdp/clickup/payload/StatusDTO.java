package uz.pdp.clickup.payload;

import lombok.Data;
import uz.pdp.clickup.entity.enums.StatusType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class StatusDTO {

    @NotBlank
    private String name;

    @NotNull
    private String color;

    @NotNull
    private UUID spaceId;

    @NotNull
    private UUID projectId;

    @NotNull
    private UUID categoryId;

    @NotNull
    private StatusType statusType;
}
