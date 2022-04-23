package uz.pdp.clickup.service;

import uz.pdp.clickup.entity.CheckList;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.ChecklistDto;

import java.util.List;
import java.util.UUID;

public interface CheckListService {

    List<CheckList> getChecklistByTask(UUID taskId);

    ApiResponse create(ChecklistDto dto);

    ApiResponse edit(UUID id, String name);

    ApiResponse delete(UUID id);
}
