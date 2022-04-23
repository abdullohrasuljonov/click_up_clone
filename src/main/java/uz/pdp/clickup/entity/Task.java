package uz.pdp.clickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.clickup.entity.template.AbsUUIDEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Task extends AbsUUIDEntity {

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    private Priority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    private Task parent;

    @Column
    private Timestamp startedDate;

    private boolean startTimeHas = false;

    private boolean dueTimeHas = false;

    @Column
    private Timestamp dueDate;

    @Column
    private Long estimateTime;

    @Column
    private Timestamp activateDate;

    public Task(String name, Status status, Category category, Priority priority, Timestamp startedDate, boolean startTimeHas, boolean dueTimeHas, Timestamp dueDate) {
        this.name = name;
        this.status = status;
        this.category = category;
        this.priority = priority;
        this.startedDate = startedDate;
        this.startTimeHas = startTimeHas;
        this.dueTimeHas = dueTimeHas;
        this.dueDate = dueDate;
    }

    public Task(String name, Priority priority, Task parent, Timestamp startedDate, boolean startTimeHas, boolean dueTimeHas, Timestamp dueDate, Long estimateTime, Timestamp activateDate) {
        this.name = name;
        this.priority = priority;
        this.parent = parent;
        this.startedDate = startedDate;
        this.startTimeHas = startTimeHas;
        this.dueTimeHas = dueTimeHas;
        this.dueDate = dueDate;
        this.estimateTime = estimateTime;
        this.activateDate = activateDate;
    }
}
