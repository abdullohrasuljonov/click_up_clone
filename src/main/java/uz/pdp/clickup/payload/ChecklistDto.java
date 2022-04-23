package uz.pdp.clickup.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class ChecklistDto {

    @NotNull
    private String name;

    @NotNull
    private UUID taskId;
}
