package uz.pdp.clickup.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkspaceDTO {

    private Long id;

    private String initialLetter;

    private String name;

    private String color;

    private UUID avatarId;

    private UUID ownerId;
}
