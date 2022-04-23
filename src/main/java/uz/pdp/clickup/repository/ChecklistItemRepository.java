package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.CheckListItem;

import java.util.List;
import java.util.UUID;

public interface ChecklistItemRepository extends JpaRepository<CheckListItem, UUID> {

    List<CheckListItem> findAllByCheckListId(UUID checklistId);
}
