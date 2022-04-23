package uz.pdp.clickup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.Comment;
import uz.pdp.clickup.entity.Task;
import uz.pdp.clickup.entity.User;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.CommentDto;
import uz.pdp.clickup.repository.CommentRepository;
import uz.pdp.clickup.repository.TaskRepository;
import uz.pdp.clickup.service.CommentService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    TaskRepository taskRepository;

    @Override
    public ApiResponse get(UUID id) {
        return null;
    }

    @Override
    public List<Comment> getByTask(UUID taskId) {
         return commentRepository.findAllByTaskId(taskId);
    }

    @Override
    public ApiResponse addComment(CommentDto dto) {
        Optional<Task> optionalTask = taskRepository.findById(dto.getTaskId());
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found!", false);
        }
        Task task = optionalTask.get();
        Comment comment = new Comment(dto.getText(), task);
        commentRepository.save(comment);
        return new ApiResponse("Successfully created!", true);
    }

    @Override
    public ApiResponse editComment(UUID id, String text) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (!optionalComment.isPresent()) {
            return new ApiResponse("Comment not found!", false);
        }
        Comment comment = optionalComment.get();
        if (comment.getCreatedBy().getId() == user.getId()) {
            comment.setText(text);
            commentRepository.save(comment);
            return new ApiResponse("Successfully edited", true);

        }
        return new ApiResponse("Error in editing!",false);
    }

    @Override
    public ApiResponse delete(UUID id) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<Comment> optionalComment = commentRepository.findById(id);
            if (!optionalComment.isPresent()) return new ApiResponse("Comment not found!", false);
            Comment comment = optionalComment.get();
            if (comment.getCreatedBy().getId() == user.getId()) {
                commentRepository.deleteById(id);
                return new ApiResponse("Successfully deleted!", true);
            }
            return new ApiResponse("Error in deleting!", false);

        } catch (Exception e) {
            return new ApiResponse("Error in deleting!", false);
        }
    }
}
