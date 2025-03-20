package taskmanagement.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import taskmanagement.model.dto.request.TaskCommentRequestDto;
import taskmanagement.model.dto.response.TaskCommentDtoResponse;
import taskmanagement.model.entity.TaskComment;
import taskmanagement.model.entity.TaskDetails;
import taskmanagement.service.TaskCommentService;
import taskmanagement.service.TaskDetailsService;
import taskmanagement.util.TaskCommentMapper;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskCommentController {
    private final TaskCommentService taskCommentService;
    private final TaskDetailsService taskDetailsService;
    private final TaskCommentMapper taskCommentMapper;

    public TaskCommentController(TaskCommentService taskCommentService, TaskDetailsService taskDetailsService, TaskCommentMapper taskCommentMapper) {
        this.taskCommentService = taskCommentService;
        this.taskDetailsService = taskDetailsService;
        this.taskCommentMapper = taskCommentMapper;
    }

    @PostMapping("/{taskId}/comments")
    public ResponseEntity<String> postComment(@PathVariable("taskId") long taskId,
                                              @Valid @RequestBody TaskCommentRequestDto taskCommentRequestDto,
                                              Authentication authentication) {
        if(!taskDetailsService.existsById(taskId)) {
            return ResponseEntity.notFound().build();
        }
        TaskDetails taskDetails = taskDetailsService.findById(taskId)
                .orElseThrow();
        TaskComment taskComment = new TaskComment(
                taskCommentRequestDto.text(),
                authentication.getName(),
                LocalDateTime.now(),
                taskDetails
        );
        taskCommentService.save(taskComment);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/{taskId}/comments")
    public ResponseEntity<List<TaskCommentDtoResponse>> findByTaskId(@PathVariable("taskId") long taskId) {
        if(!taskDetailsService.existsById(taskId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .body(
                        taskCommentService.findByTaskId(taskId).stream()
                                .map(taskCommentMapper::toDto)
                                .toList()
                );
    }
}
