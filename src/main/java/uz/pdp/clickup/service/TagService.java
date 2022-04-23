package uz.pdp.clickup.service;

import uz.pdp.clickup.entity.Tag;
import uz.pdp.clickup.payload.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface TagService {

    List<Tag> get(Long workspaceId);

    ApiResponse editTag(Long tagId, Tag tag);

    ApiResponse deleteTag(Long tagId);

    ApiResponse addTag(Tag tag);
}
