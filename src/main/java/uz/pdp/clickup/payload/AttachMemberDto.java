package uz.pdp.clickup.payload;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class AttachMemberDto {

    private List<UUID> members;
}
