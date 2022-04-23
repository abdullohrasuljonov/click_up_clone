package uz.pdp.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickup.entity.Tag;
import uz.pdp.clickup.entity.Workspace;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Long> {

    List<Tag> findAllByWorkspaceId(Long workspace_id);

    boolean existsByNameAndWorkspace(String name, Workspace workspace);

}
