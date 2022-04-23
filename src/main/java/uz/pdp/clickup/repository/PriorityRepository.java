package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.Priority;

import java.util.UUID;

public interface PriorityRepository extends JpaRepository<Priority, Long> {
}
