package uz.pdp.clickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.clickup.entity.enums.AccessType;
import uz.pdp.clickup.entity.template.AbsUUIDEntity;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "space_id"}))
public class Project extends AbsUUIDEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    private Space space;

    @Column
    private boolean archived = false;

    @Enumerated(EnumType.STRING)
    private AccessType accessType;

    @OneToMany(mappedBy = "project", cascade = CascadeType.PERSIST)
    private List<Category> categories;
}
