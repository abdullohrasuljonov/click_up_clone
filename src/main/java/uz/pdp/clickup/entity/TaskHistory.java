
package uz.pdp.clickup.entity;

import lombok.*;
import uz.pdp.clickup.entity.template.AbsUUIDEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "taskHistories")
public class TaskHistory extends AbsUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Task task;

    @Column
    private String changeFieldName;

    @Column
    private String before;

    @Column
    private String after;

    @Column
    private String data;
}
