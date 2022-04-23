package uz.pdp.clickup.payload;

import lombok.Data;

import javax.validation.constraints.*;
import java.util.UUID;

@Data
public class CategoryDto {

    @NotBlank
    private String name;

    @NotNull
    private UUID projectId;

    @NotNull
    private UUID spaceId;
}
