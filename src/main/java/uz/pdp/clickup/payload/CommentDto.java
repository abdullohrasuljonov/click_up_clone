package uz.pdp.clickup.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class CommentDto {

    private String text;

    private UUID taskId;
}
