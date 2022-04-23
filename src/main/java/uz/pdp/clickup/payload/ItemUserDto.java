package uz.pdp.clickup.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class ItemUserDto {

    private UUID itemId;

    private UUID userId;
}
