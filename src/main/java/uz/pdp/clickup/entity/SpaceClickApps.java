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
public class SpaceClickApps extends AbsUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private ClickApps clickApps;

    @ManyToOne(fetch = FetchType.LAZY)
    private Space space;
}
