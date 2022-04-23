package uz.pdp.clickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.clickup.entity.enums.DependencyType;
import uz.pdp.clickup.entity.template.AbsUUIDEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class TaskDependency extends AbsUUIDEntity {

    @ManyToOne
    private Task task;

    @ManyToOne
    private Task dependencyTask;

    @Enumerated(EnumType.STRING)
    private DependencyType dependencyType;

}
