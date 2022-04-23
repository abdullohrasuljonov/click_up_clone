package uz.pdp.clickup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.*;
import uz.pdp.clickup.entity.enums.AccessType;
import uz.pdp.clickup.payload.*;
import uz.pdp.clickup.repository.*;
import uz.pdp.clickup.service.SpaceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SpaceServiceImpl implements SpaceService {

    @Autowired
    SpaceRepository spaceRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    WorkspaceRepository workspaceRepository;
    @Autowired
    ViewRepository viewRepository;
    @Autowired
    ClickAppsRepository clickAppsRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    WorkspaceUserRepository workspaceUserRepository;
    @Autowired
    SpaceClickAppsRepository spaceClickAppsRepository;
    @Autowired
    IconRepository iconRepository;
    @Autowired
    SpaceUserRepository spaceUserRepository;
    @Autowired
    SpaceViewRepository spaceViewRepository;

    @Override
    public Space get(UUID id) {
        Optional<Space> optionalSpace = spaceRepository.findById(id);
        return optionalSpace.orElse(null);
    }

    @Override
    public List<Space> getWorkspaceId(Long workspaceId) {
        boolean exists = workspaceRepository.existsById(workspaceId);
        if (!exists)
            return null;
        return spaceRepository.findAllByWorkspaceId(workspaceId);
    }

    @Override
    public ApiResponse addSpace(SpaceDto dto, User user) {
        if (spaceRepository.existsByNameAndWorkspaceId(dto.getName(), dto.getWorkspaceId())) {
            return new ApiResponse("Space has already exist!", false);
        }
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(dto.getWorkspaceId());
        if (!optionalWorkspace.isPresent()) {
            return new ApiResponse("Workspace not found!", false);
        }
        Workspace workspace = optionalWorkspace.get();
        Space space = new Space(
                dto.getName(),
                dto.getColor(),
                workspace,
                dto.getIconId() == null ? null : iconRepository.findById(dto.getIconId()).orElseThrow(() -> new ResourceNotFoundException("icon")),
                dto.getAvatarId() == null ? null : attachmentRepository.findById(dto.getAvatarId()).orElseThrow(() -> new ResourceNotFoundException("attachment")),
                user,
                dto.getAccessType());
        spaceRepository.save(space);

        List<SpaceUser> members = new ArrayList<>();
        if (dto.getAccessType().equals(AccessType.PUBLIC)) {
            for (WorkspaceUser workspaceUser : workspaceUserRepository.findAllByWorkspaceId(workspace.getId())) {
                members.add(new SpaceUser(workspaceUser.getUser(), space));
            }
            spaceUserRepository.saveAll(members);
        } else {
            for (UUID memberId : dto.getMembers()) {
                if (workspaceUserRepository.findByWorkspaceIdAndUserId(workspace.getId(), memberId).isPresent()) {
                    members.add(new SpaceUser(userRepository.getById(memberId), space));
                }
            }
            spaceUserRepository.save(new SpaceUser(user, space));
        }
        spaceClickAppsRepository.save(new SpaceClickApps(clickAppsRepository.getByName("Priority"), space));
        spaceViewRepository.save(new SpaceView(viewRepository.getByName("List"), space));

        return new ApiResponse("Successfully created!", true);
    }

    @Override
    public ApiResponse editSpace(SpaceDto dto, UUID id) {
        if (spaceRepository.existsByNameAndWorkspaceIdAndIdNot(dto.getName(), dto.getWorkspaceId(), id)) {
            return new ApiResponse("Space has already exist!", false);
        }

        Optional<Space> optionalSpace = spaceRepository.findById(id);
        if (!optionalSpace.isPresent()) {
            return new ApiResponse("Space not found!", false);
        }
        Space space = optionalSpace.get();
        space.setAvatar(dto.getAvatarId() == null ? null : attachmentRepository.findById(dto.getAvatarId()).orElseThrow(() -> new ResourceNotFoundException("attachment")));
        space.setIcon(dto.getIconId() == null ? null : iconRepository.findById(dto.getIconId()).orElseThrow(() -> new ResourceNotFoundException("icon")));
        space.setColor(dto.getColor());
        space.setName(dto.getName());

        if (!space.getAccessType().equals(dto.getAccessType())) {
            space.setAccessType(dto.getAccessType());
            editMembers(space, dto.getMembers(), "SAVE");
        }

        return new ApiResponse("Updated", true);
    }

    @Override
    public ApiResponse attachMembers(AttachMemberDto dto, UUID id) {
        Optional<Space> optionalSpace = spaceRepository.findById(id);
        if (!optionalSpace.isPresent()) {
            return new ApiResponse("Space not found!", false);
        }
        Space space = optionalSpace.get();
        editMembers(space, dto.getMembers(), "SAVE");
        return new ApiResponse("OK", true);
    }

    @Override
    public List<View> getViewBySpace(UUID id) {
        Optional<Space> optionalSpace = spaceRepository.findById(id);
        if (!optionalSpace.isPresent()) {
            return null;
        }
        Space space = optionalSpace.get();
        List<SpaceView> spaceViews = spaceViewRepository.findAllBySpaceId(space.getId());
        List<View> views = new ArrayList<>();
        for (SpaceView spaceView : spaceViews) {
            views.add(spaceView.getView());
        }
        return views;
    }

    @Override
    public ApiResponse attachViews(AttachViewsDto dto, UUID id) {
        Optional<Space> optionalSpace = spaceRepository.findById(id);
        if (!optionalSpace.isPresent()) {
            return new ApiResponse("Space not found!", false);
        }
        Space space = optionalSpace.get();
        editViews(space, dto.getViews(), "SAVE");
        return new ApiResponse("OK", true);
    }

    @Override
    public ApiResponse attachClickApp(AttachClickAppDto dto, UUID id) {
        Optional<Space> optionalSpace = spaceRepository.findById(id);
        if (!optionalSpace.isPresent()) {
            return new ApiResponse("Space not found!", false);
        }
        Space space = optionalSpace.get();
        editClickApp(space, dto.getClickApps(), "SAVE");
        return new ApiResponse("OK", true);
    }

    @Override
    public ApiResponse detachMembers(AttachMemberDto dto, UUID id) {
        return null;
    }

    @Override
    public ApiResponse detachViews(AttachViewsDto dto, UUID id) {
        Optional<Space> optionalSpace = spaceRepository.findById(id);
        if (!optionalSpace.isPresent()) {
            return new ApiResponse("Space not found!", false);
        }
        Space space = optionalSpace.get();
        editViews(space, dto.getViews(), "DELETE");
        return new ApiResponse("OK", true);
    }

    @Override
    public ApiResponse detachClickApp(AttachClickAppDto dto, UUID id) {
        Optional<Space> optionalSpace = spaceRepository.findById(id);
        if (!optionalSpace.isPresent()) {
            return new ApiResponse("Space not found!", false);
        }
        Space space = optionalSpace.get();
        editClickApp(space, dto.getClickApps(), "DELETE");
        return new ApiResponse("OK", true);
    }

    @Override
    public ApiResponse delete(UUID id) {
        if (!spaceRepository.existsById(id)){
            return new ApiResponse("Space not found!", false);
        }
        spaceRepository.deleteById(id);
        return new ApiResponse("Successfully deleted!", true);
    }



    private void editMembers(Space space, List<UUID> members, String operation) {
        List<SpaceUser> spaceUsers = spaceUserRepository.findAllBySpaceId(space.getId());
        if (operation.equals("SAVE")) {
            List<SpaceUser> newMembers = new ArrayList<>(spaceUsers);
            for (UUID memberId : members) {
                if (workspaceUserRepository.findByWorkspaceIdAndUserId(space.getWorkspace().getId(), memberId).isPresent()) {
                    boolean has = false;
                    for (SpaceUser spaceUser : spaceUsers) {
                        if (spaceUser.getMember().getId().equals(memberId)) {
                            has = true;
                            break;
                        }
                    }
                    if (!has) {
                        newMembers.add(new SpaceUser(userRepository.getById(memberId), space));
                    }
                }
            }
            spaceUserRepository.saveAll(newMembers);
        } else if (operation.equals("DELETE")) {
            List<SpaceUser> deleting = new ArrayList<>();
            for (UUID memberId : members) {
                for (SpaceUser spaceUser : spaceUsers) {
                    if (workspaceUserRepository.findByWorkspaceIdAndUserId(space.getWorkspace().getId(), memberId).isPresent()) {
                        if (memberId.equals(spaceUser.getMember().getId())) {
                            deleting.add(spaceUser);
                        }
                    }
                }
            }
            spaceUserRepository.deleteAll(deleting);
        }
    }

    private void editViews(Space space, List<Long> views, String operation) {
        List<SpaceView> spaceViews = spaceViewRepository.findAllBySpaceId(space.getId());
        if (operation.equals("SAVE")) {
            List<SpaceView> newViews = new ArrayList<>(spaceViews);
            for (Long viewId : views) {
                if (viewRepository.findById(viewId).isPresent()) {
                    boolean has = false;
                    for (SpaceView spaceView : spaceViews) {
                        if (spaceView.getView().getId().equals(viewId)) {
                            has = true;
                            break;
                        }
                    }
                    if (!has) {
                        newViews.add(new SpaceView(viewRepository.getById(viewId), space));
                    }
                }
            }
            spaceViewRepository.saveAll(newViews);
        } else if (operation.equals("DELETE")) {
            List<SpaceView> deleting = new ArrayList<>();
            for (Long viewId : views) {
                for (SpaceView spaceView : spaceViews) {
                    if (viewRepository.findById(viewId).isPresent() && viewId.equals(spaceView.getView().getId())) {
                        deleting.add(spaceView);
                    }
                }
            }
            spaceViewRepository.deleteAll(deleting);
        }
    }

    private void editClickApp(Space space, List<Long> clickApps, String operation) {
        List<SpaceClickApps> spaceClickApps = spaceClickAppsRepository.findAllBySpaceId(space.getId());
        if (operation.equals("SAVE")) {
            List<SpaceClickApps> newClickApps = new ArrayList<>(spaceClickApps);
            for (Long clickApp : clickApps) {
                if (clickAppsRepository.findById(clickApp).isPresent()) {
                    boolean has = false;
                    for (SpaceClickApps spaceClickApps1 : spaceClickApps) {
                        if (spaceClickApps1.getClickApps().getId().equals(clickApp)) {
                            has = true;
                            break;
                        }
                    }
                    if (!has) {
                        newClickApps.add(new SpaceClickApps(clickAppsRepository.getById(clickApp), space));
                    }
                }
            }
            spaceClickAppsRepository.saveAll(newClickApps);
        } else if (operation.equals("DELETE")) {
            List<SpaceClickApps> deleting = new ArrayList<>();
            for (Long clickApp : clickApps) {
                for (SpaceClickApps clickApps1 : spaceClickApps) {
                    if (clickAppsRepository.findById(clickApp).isPresent() && clickApp.equals(clickApps1.getClickApps().getId())) {
                        deleting.add(clickApps1);
                    }
                }
            }
            spaceClickAppsRepository.deleteAll(deleting);
        }
    }


}
