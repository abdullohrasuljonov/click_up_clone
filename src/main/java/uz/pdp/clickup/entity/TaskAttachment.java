package uz.pdp.clickup.entity;

import lombok.*;
import uz.pdp.clickup.entity.template.AbsUUIDEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class TaskAttachment extends AbsUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    private Attachment attachment;

    @Column
    private boolean pinCoverImage = false;

    public TaskAttachment(Task task, Attachment attachment) {
        this.task = task;
        this.attachment = attachment;
    }

}
