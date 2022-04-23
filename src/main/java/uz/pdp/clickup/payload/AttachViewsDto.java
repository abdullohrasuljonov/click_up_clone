package uz.pdp.clickup.payload;

import lombok.Data;

import java.util.List;

@Data
public class AttachViewsDto {

    private List<Long> views;
}
