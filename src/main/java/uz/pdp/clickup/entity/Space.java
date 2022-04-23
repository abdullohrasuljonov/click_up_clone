package uz.pdp.clickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.clickup.entity.enums.AccessType;
import uz.pdp.clickup.entity.template.AbsUUIDEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "workspace_id"}))
public class Space extends AbsUUIDEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    @ManyToOne
    private Workspace workspace;

    @Column
    private String initialLetter;

    @PrePersist
    @PreUpdate
    public void setInitialLetterMyMethod() {
        this.initialLetter = name.substring(0, 1);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private Icon icon;

    @OneToOne(fetch = FetchType.LAZY)
    private Attachment avatar;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @Enumerated(EnumType.STRING)
    private AccessType accessType;

    public Space(String name, String color, Workspace workspace, Icon icon, Attachment avatar, User owner, AccessType accessType) {
        this.name = name;
        this.color = color;
        this.workspace = workspace;
        this.icon = icon;
        this.avatar = avatar;
        this.owner = owner;
        this.accessType = accessType;
    }
}
