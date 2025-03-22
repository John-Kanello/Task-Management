package taskmanagement.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import taskmanagement.model.Role;
import taskmanagement.model.dto.request.TaskAssigneeDto;
import taskmanagement.model.dto.request.TaskDetailsRequestDto;
import taskmanagement.model.dto.request.TaskStatusDto;
import taskmanagement.model.dto.response.TaskDetailsResponseDto;
import taskmanagement.model.dto.response.TaskDetailsCommentsResponseDto;
import taskmanagement.model.entity.TaskDetails;
import taskmanagement.model.entity.TaskManagementUser;
import taskmanagement.service.TaskDetailsService;
import taskmanagement.service.TaskManagementUserService;
import taskmanagement.util.AuthenticationUtils;
import taskmanagement.util.mapper.TaskDetailsMapper;
import taskmanagement.util.mapper.TaskDetailsCommentsMapper;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskManagementController {
    private final TaskDetailsService taskDetailsService;
    private final TaskManagementUserService taskManagementUserService;
    private final TaskDetailsMapper taskDetailsMapper;    
    private final TaskDetailsCommentsMapper taskDetailsCommentsMapper;

    public TaskManagementController(TaskDetailsService taskDetailsService,
                                    TaskManagementUserService taskManagementUserService,
                                    TaskDetailsMapper taskDetailsMapper,
                                    TaskDetailsCommentsMapper taskDetailsCommentsMapper) {
        this.taskDetailsService = taskDetailsService;
        this.taskManagementUserService = taskManagementUserService;
        this.taskDetailsMapper = taskDetailsMapper;
        this.taskDetailsCommentsMapper = taskDetailsCommentsMapper;
    }

    @GetMapping
    public ResponseEntity<List<TaskDetailsCommentsResponseDto>> getTasks(@RequestParam(value = "author", required = false) String author,
                                                                         @RequestParam(value = "assignee", required = false) String assignee) {
        if(author == null && assignee == null) {
            return createTaskDetailsCommentsDtoResponse(taskDetailsService.findAll());
        }
        if(author == null) {
            return createTaskDetailsCommentsDtoResponse(taskDetailsService.findByAssignee(assignee));
        }
        if(assignee == null) {
            return createTaskDetailsCommentsDtoResponse(taskDetailsService.findByAuthor(author));
        }
        return createTaskDetailsCommentsDtoResponse(taskDetailsService.findByAuthorAndAssignee(author, assignee));
    }

    public ResponseEntity<List<TaskDetailsCommentsResponseDto>> createTaskDetailsCommentsDtoResponse(List<TaskDetails> taskDetails) {
        return ResponseEntity.ok()
                .body(taskDetails.stream()
                        .map(taskDetailsCommentsMapper::toDto)
                        .toList());
    }

    @PostMapping
    public ResponseEntity<TaskDetailsResponseDto> createTask(@Valid @RequestBody TaskDetailsRequestDto taskDetailsRequestDto,
                                                             Authentication authentication) {
        String email = authentication.getName();
        Optional<TaskManagementUser> taskManagementUserOptional = taskManagementUserService.findByEmail(email);
        if(taskManagementUserOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        TaskManagementUser taskManagementUser = taskManagementUserOptional.orElseThrow();
        taskDetailsRequestDto.setAuthor(taskManagementUser);
        TaskDetails savedTaskDetails = taskDetailsService.save(taskDetailsRequestDto);
        return ResponseEntity.ok()
                .body(taskDetailsMapper.toDto(savedTaskDetails));
    }

    @PutMapping("/{taskId}/assign")
    public ResponseEntity<TaskDetailsResponseDto> updateTaskAssignee(@PathVariable("taskId") long taskId,
                                                                            @Valid @RequestBody TaskAssigneeDto taskAssigneeDto,
                                                                            Authentication authentication) {
        if(!taskDetailsService.existsById(taskId)) {
            return ResponseEntity.notFound().build();
        }
        String assignee = taskAssigneeDto.assignee();
        if(!"none".equals(assignee) && !taskManagementUserService.existsByEmail(assignee)) {
            return ResponseEntity.notFound().build();
        }
        String userEmail = authentication.getName();
        TaskManagementUser user = taskManagementUserService.findByEmail(userEmail)
                .orElseThrow();
        TaskDetails taskDetails = taskDetailsService.findById(taskId)
                .orElseThrow();
        if(user.getRole() != Role.ADMIN && user.getId() != taskDetails.getAuthor().getId()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        taskDetails.setAssignee(assignee);
        TaskDetails updatedTaskDetails = taskDetailsService.save(taskDetails);
        return ResponseEntity.ok()
                .body(taskDetailsMapper.toDto(updatedTaskDetails));
    }

    @PutMapping("/{taskId}/status")
    public ResponseEntity<TaskDetailsResponseDto> updateTaskStatus(@PathVariable("taskId") long taskId,
                                                                          @Valid @RequestBody TaskStatusDto taskStatusDto,
                                                                          Authentication authentication) {
        if(!taskDetailsService.existsById(taskId)) {
            return ResponseEntity.notFound().build();
        }
        TaskDetails taskDetails = taskDetailsService.findById(taskId).orElseThrow();
        String userEmail = authentication.getName();
        TaskManagementUser user = taskManagementUserService.findByEmail(userEmail).orElseThrow();
        if(user.getRole() != Role.ADMIN &&
                user.getId() != taskDetails.getAuthor().getId() &&
                !userEmail.equalsIgnoreCase(taskDetails.getAssignee())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        taskDetails.setStatus(taskStatusDto.status().name());
        TaskDetails updatedTaskDetails = taskDetailsService.save(taskDetails);
        return ResponseEntity.ok()
                .body(taskDetailsMapper.toDto(updatedTaskDetails));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteById(@PathVariable("taskId") long taskId,
                                             Authentication authentication) {
        if(!taskDetailsService.existsById(taskId)) {
            return ResponseEntity.notFound().build();
        }
        TaskDetails taskDetails = taskDetailsService.findById(taskId).orElseThrow();
        String userEmail = authentication.getName();
        boolean hasAdminRole = AuthenticationUtils.hasAdminRole(authentication);
        if(!hasAdminRole && !taskDetails.getAuthor().getEmail().equalsIgnoreCase(userEmail)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        taskDetailsService.deleteById(taskId);
        return ResponseEntity.ok()
                .build();
    }
}
