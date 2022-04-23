package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.Space;

import java.util.List;
import java.util.UUID;

public interface SpaceRepository extends JpaRepository<Space, UUID> {

    List<Space> findAllByWorkspaceId(Long workspace_id);

    boolean existsByNameAndWorkspaceId(String name, Long workspaceId);

    boolean existsByNameAndWorkspaceIdAndIdNot(String name, Long workspaceId, UUID id);
}
