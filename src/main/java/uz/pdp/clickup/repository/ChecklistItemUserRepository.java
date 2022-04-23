package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.CheckListItemUser;

import java.util.UUID;

public interface ChecklistItemUserRepository extends JpaRepository<CheckListItemUser, UUID> {

    boolean existsByUserIdAndCheckListItemId(UUID userId, UUID itemId);

    void deleteByUserIdAndCheckListItemId(UUID userId, UUID itemId);
}
