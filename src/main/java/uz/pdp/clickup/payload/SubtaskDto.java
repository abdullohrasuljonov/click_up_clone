package uz.pdp.clickup.payload;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.pdp.clickup.entity.template.AbsUUIDEntity;

import java.sql.Timestamp;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class SubtaskDto extends AbsUUIDEntity {

    private String name;

    private Long priorityId;

    private UUID parentId;

    private Timestamp startedDate;

    private boolean startTimeHas = false;

    private boolean dueTimeHas = false;

    private Timestamp dueDate;

    private Long estimateTime;

    private Timestamp activateDate;
}
