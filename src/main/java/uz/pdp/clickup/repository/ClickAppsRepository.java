package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.ClickApps;

public interface ClickAppsRepository extends JpaRepository<ClickApps,Long> {
    ClickApps getByName(String priority);
}
