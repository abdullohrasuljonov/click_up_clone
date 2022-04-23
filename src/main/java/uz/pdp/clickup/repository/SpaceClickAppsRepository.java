package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.SpaceClickApps;

import java.util.List;
import java.util.UUID;

public interface SpaceClickAppsRepository extends JpaRepository<SpaceClickApps, UUID> {
    List<SpaceClickApps> findAllBySpaceId(UUID id);
}
