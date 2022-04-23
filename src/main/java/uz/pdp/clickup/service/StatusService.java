package uz.pdp.clickup.service;

import uz.pdp.clickup.entity.Status;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.StatusDTO;
import uz.pdp.clickup.payload.StatusEditDTO;

import java.util.List;
import java.util.UUID;

public interface StatusService {

    Status get(UUID id);

    List<Status> getByCategory(UUID categoryId);

    ApiResponse create(StatusDTO dto);

    ApiResponse edit(UUID id, StatusEditDTO dto);

    ApiResponse delete(UUID id);
}
