package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.SpaceView;

import java.util.List;
import java.util.UUID;

public interface SpaceViewRepository extends JpaRepository<SpaceView, UUID> {
    List<SpaceView> findAllBySpaceId(UUID id);
}
