package uz.pdp.clickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickup.entity.Tag;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.service.TagService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping("/{workspaceId}")
    public HttpEntity<?> get(@PathVariable Long workspaceId) {
        List<Tag> tags = tagService.get(workspaceId);
        return ResponseEntity.ok(tags);
    }

    @PostMapping
    public HttpEntity<?> addTag(@RequestBody Tag tag) {
        ApiResponse apiResponse = tagService.addTag(tag);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{tagId}")
    public HttpEntity<?> editTag(@PathVariable Long tagId, @RequestBody Tag tag) {
        ApiResponse apiResponse = tagService.editTag(tagId, tag);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteTag(@PathVariable(value = "id") Long tagId) {
        ApiResponse apiResponse = tagService.deleteTag(tagId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
