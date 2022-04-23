package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.SpaceUser;

import java.util.List;
import java.util.UUID;

public interface SpaceUserRepository extends JpaRepository<SpaceUser, UUID> {
    List<SpaceUser> findAllBySpaceId(UUID id);

    boolean existsByMemberIdAndSpaceId(UUID uuid, UUID id);
}
