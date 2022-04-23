package uz.pdp.clickup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.Tag;
import uz.pdp.clickup.entity.User;
import uz.pdp.clickup.entity.Workspace;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.repository.TagRepository;
import uz.pdp.clickup.repository.WorkspaceRepository;
import uz.pdp.clickup.service.TagService;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepository;
    @Autowired
    WorkspaceRepository workspaceRepository;

    @Override
    public List<Tag> get(Long workspaceId) {
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(workspaceId);
        if (!optionalWorkspace.isPresent())
            return null;
        return tagRepository.findAllByWorkspaceId(workspaceId);
    }

    @Override
    public ApiResponse editTag(Long tagId, Tag tag) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Tag> optionalTag = tagRepository.findById(tagId);
        if (!optionalTag.isPresent()) {
            return new ApiResponse("Tag not found!", false);
        }
        Tag newTag = optionalTag.get();
        if (newTag.getCreatedBy().getId() == user.getId()) {
            newTag.setWorkspace(tag.getWorkspace());
            newTag.setName(tag.getName());
            newTag.setColor(tag.getColor());
            tagRepository.save(newTag);
            return new ApiResponse("Successfully edited", true);

        }
        return new ApiResponse("Error in editing!",false);

    }

    @Override
    public ApiResponse deleteTag(Long tagId) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<Tag> optionalTag = tagRepository.findById(tagId);
            if (!optionalTag.isPresent()) return new ApiResponse("Tag not found!", false);
            Tag tag = optionalTag.get();
            if (tag.getCreatedBy().getId() == user.getId()) {
                tagRepository.deleteById(tagId);
                return new ApiResponse("Successfully deleted!", true);
            }
            return new ApiResponse("Error in deleting!", false);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting!", false);
        }
    }

    @Override
    public ApiResponse addTag(Tag tag) {
        boolean exists = tagRepository.existsByNameAndWorkspace(tag.getName(),tag.getWorkspace());
        if (exists)
            return new ApiResponse("Tag already exist this workspace!",false);
        Tag addTag = new Tag(tag.getName(),tag.getColor(),tag.getWorkspace());
        tagRepository.save(addTag);
        return new ApiResponse("Successfully created!",true);
    }
}
