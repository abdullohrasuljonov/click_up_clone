package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.ProjectUser;

import java.util.UUID;

public interface ProjectUserRepository extends JpaRepository<ProjectUser, UUID> {

    boolean existsByProjectIdAndMemberId(UUID id, UUID memberId);

    void deleteAllByProjectIdAndMemberId(UUID id, UUID memberId);
}
