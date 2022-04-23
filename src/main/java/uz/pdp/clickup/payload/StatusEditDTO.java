package uz.pdp.clickup.payload;

import lombok.Data;
import uz.pdp.clickup.entity.enums.StatusType;

import javax.validation.constraints.*;

@Data
public class StatusEditDTO {

    @NotBlank
    private String name;

    @NotNull
    private String color;

    @NotNull
    private StatusType statusType;
}
