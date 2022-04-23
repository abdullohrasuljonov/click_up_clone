package uz.pdp.clickup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.CheckList;
import uz.pdp.clickup.entity.CheckListItem;
import uz.pdp.clickup.entity.CheckListItemUser;
import uz.pdp.clickup.entity.User;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.ChecklistItemDto;
import uz.pdp.clickup.repository.ChecklistItemUserRepository;
import uz.pdp.clickup.payload.ItemUserDto;
import uz.pdp.clickup.repository.ChecklistItemRepository;
import uz.pdp.clickup.repository.ChecklistRepository;
import uz.pdp.clickup.repository.UserRepository;
import uz.pdp.clickup.service.CheckListItemService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CheckListItemServiceImpl implements CheckListItemService {

    @Autowired
    ChecklistItemRepository checklistItemRepository;

    @Autowired
    ChecklistRepository checklistRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChecklistItemUserRepository checklistItemUserRepository;

    @Override
    public List<CheckListItem> getByChecklist(UUID checklistId) {
        if (!checklistRepository.existsById(checklistId)) {
            return null;
        }
        return checklistItemRepository.findAllByCheckListId(checklistId);
    }

    @Override
    public ApiResponse create(ChecklistItemDto dto) {
        Optional<CheckList> optionalCheckList = checklistRepository.findById(dto.getChecklistId());
        if (!optionalCheckList.isPresent()) {
            return new ApiResponse("Checklist not found!", false);
        }
        CheckList checkList = optionalCheckList.get();
        CheckListItem item = new CheckListItem(dto.getName(), checkList, false);
        checklistItemRepository.save(item);
        return new ApiResponse("Successfully created!", true);
    }

    @Override
    public ApiResponse assign(ItemUserDto dto) {
        Optional<CheckListItem> optionalCheckListItem = checklistItemRepository.findById(dto.getItemId());
        if (!optionalCheckListItem.isPresent()) {
            return new ApiResponse("CheckListItem not found!", false);
        }
        Optional<User> optionalUser = userRepository.findById(dto.getUserId());
        if (!optionalUser.isPresent()) {
            return new ApiResponse("User not found!", false);
        }
        CheckListItem item = optionalCheckListItem.get();
        User user = optionalUser.get();
        checklistItemUserRepository.save(new CheckListItemUser(item, user));
        return new ApiResponse("Assigned!", true);
    }

    @Override
    public ApiResponse removeUser(ItemUserDto dto) {
        if (!checklistItemUserRepository.existsByUserIdAndCheckListItemId(dto.getUserId(),dto.getItemId())){
            return new ApiResponse("Not found!", false);
        }
        checklistItemUserRepository.deleteByUserIdAndCheckListItemId(dto.getUserId(),dto.getItemId());
        return new ApiResponse("Successfully removed!", true);
    }

    @Override
    public ApiResponse edit(UUID id, String name) {
        Optional<CheckListItem> optionalCheckListItem = checklistItemRepository.findById(id);
        if (!optionalCheckListItem.isPresent()) {
            return new ApiResponse("CheckListItem not found!", false);
        }
        CheckListItem item = optionalCheckListItem.get();
        item.setName(name);
        checklistItemRepository.save(item);
        return new ApiResponse("Successfully updated!", true);
    }

    @Override
    public ApiResponse resolve(UUID id) {
        Optional<CheckListItem> optionalCheckListItem = checklistItemRepository.findById(id);
        if (!optionalCheckListItem.isPresent()) {
            return new ApiResponse("CheckListItem not found!", false);
        }
        CheckListItem item = optionalCheckListItem.get();
        item.setResolved(true);
        checklistItemRepository.save(item);
        return new ApiResponse("Successfully resolved!", true);
    }

    @Override
    public ApiResponse delete(UUID id) {
        Optional<CheckListItem> optionalCheckListItem = checklistItemRepository.findById(id);
        if (!optionalCheckListItem.isPresent()) {
            return new ApiResponse("Item not found!", false);
        }
        checklistItemRepository.deleteById(id);
        return new ApiResponse("Successfully deleted!", true);
    }
}
