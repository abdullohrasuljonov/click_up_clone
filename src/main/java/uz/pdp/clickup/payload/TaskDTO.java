package uz.pdp.clickup.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@Data
public class TaskDTO {

    @NotBlank
    private String name;

    @NotNull
    private UUID statusId;

    @NotNull
    private UUID categoryId;

    private Long priorityId;

    private Timestamp startDate;

    private boolean startTimeHas;

    private boolean dueTimeHas;

    private Timestamp dueDate;
}
