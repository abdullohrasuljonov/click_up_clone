package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.View;

public interface ViewRepository extends JpaRepository<View,Long> {

    View getByName(String list);
}
