package uz.pdp.clickup.payload;

import lombok.Data;
import uz.pdp.clickup.entity.enums.AddType;

import java.sql.Timestamp;

@Data
public class DueDateDto {

    private Timestamp startedDate;

    private boolean startTimeHas = false;

    private boolean dueTimeHas = false;

    private Timestamp dueDate;

    private AddType addType;
}
