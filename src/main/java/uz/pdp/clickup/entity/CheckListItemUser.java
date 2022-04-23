package uz.pdp.clickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.clickup.entity.template.AbsUUIDEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class CheckListItemUser extends AbsUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private CheckListItem checkListItem;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
