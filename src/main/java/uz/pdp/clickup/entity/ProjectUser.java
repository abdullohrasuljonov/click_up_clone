package uz.pdp.clickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.clickup.entity.enums.WorkspacePermissionName;
import uz.pdp.clickup.entity.template.AbsUUIDEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ProjectUser extends AbsUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private User member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @Enumerated(EnumType.STRING)
    private WorkspacePermissionName permission;
}
