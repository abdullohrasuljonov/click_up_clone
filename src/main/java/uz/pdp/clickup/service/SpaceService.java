package uz.pdp.clickup.service;

import uz.pdp.clickup.entity.Space;
import uz.pdp.clickup.entity.User;
import uz.pdp.clickup.entity.View;
import uz.pdp.clickup.payload.*;

import java.util.List;
import java.util.UUID;

public interface SpaceService {

    Space get(UUID id);

    List<Space> getWorkspaceId(Long workspaceId);

    ApiResponse addSpace(SpaceDto dto, User user);

    ApiResponse editSpace(SpaceDto dto, UUID id);

    ApiResponse attachMembers(AttachMemberDto dto, UUID id);

    List<View> getViewBySpace(UUID id);

    ApiResponse attachViews(AttachViewsDto dto, UUID id);

    ApiResponse attachClickApp(AttachClickAppDto dto, UUID id);

    ApiResponse detachMembers(AttachMemberDto dto, UUID id);

    ApiResponse detachViews(AttachViewsDto dto, UUID id);

    ApiResponse detachClickApp(AttachClickAppDto dto, UUID id);

    ApiResponse delete(UUID id);
}
