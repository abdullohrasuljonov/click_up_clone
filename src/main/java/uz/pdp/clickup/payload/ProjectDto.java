package uz.pdp.clickup.payload;

import lombok.Data;
import uz.pdp.clickup.entity.enums.AccessType;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
public class ProjectDto {

    @NotNull
    private String name;

    private String color;

    @NotNull
    private UUID spaceId;

    @NotNull
    private AccessType accessType;

    @NotNull
    private List<String> lists;
}
