package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.Workspace;

import java.util.UUID;

public interface WorkspaceRepository extends JpaRepository<Workspace,Long> {

    boolean existsByOwnerIdAndName(UUID owner_id, String name);

    boolean existsByOwnerIdAndNameAndIdNot(UUID owner_id, String name, Long id);
}
