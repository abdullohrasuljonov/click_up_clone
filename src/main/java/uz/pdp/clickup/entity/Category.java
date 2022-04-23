package uz.pdp.clickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.clickup.entity.template.AbsUUIDEntity;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "project_id"}))
public class Category extends AbsUUIDEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @Column
    private boolean archived = false;

    @Transient
    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST)
    private List<Status> statuses;

    public Category(String name, Project project) {
        this.name = name;
        this.project = project;
    }
}
