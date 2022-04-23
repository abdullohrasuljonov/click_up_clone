package uz.pdp.clickup.service;

import uz.pdp.clickup.entity.CheckList;
import uz.pdp.clickup.entity.CheckListItem;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.ChecklistItemDto;
import uz.pdp.clickup.payload.ItemUserDto;

import java.util.List;
import java.util.UUID;

public interface CheckListItemService {

    List<CheckListItem> getByChecklist(UUID checklistId);

    ApiResponse create(ChecklistItemDto dto);

    ApiResponse assign(ItemUserDto dto);

    ApiResponse removeUser(ItemUserDto dto);

    ApiResponse edit(UUID id, String name);

    ApiResponse resolve(UUID id);

    ApiResponse delete(UUID id);
}
