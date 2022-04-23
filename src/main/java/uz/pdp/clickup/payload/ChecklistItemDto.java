package uz.pdp.clickup.payload;

import lombok.Data;

import javax.validation.constraints.*;
import java.util.UUID;

@Data
public class ChecklistItemDto {

    @NotBlank
    private String name;

    @NotNull
    private UUID checklistId;
}
