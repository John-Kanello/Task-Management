package taskmanagement.service;

import org.springframework.stereotype.Service;
import taskmanagement.model.entity.TaskComment;
import taskmanagement.repository.TaskCommentRepository;

import java.util.List;

@Service
public class TaskCommentService {
    private final TaskCommentRepository taskCommentRepository;

    public TaskCommentService(TaskCommentRepository taskCommentRepository) {
        this.taskCommentRepository = taskCommentRepository;
    }

    public List<TaskComment> findByTaskId(long taskId) {
        return taskCommentRepository.findByTaskIdOrderByTimestampDescending(taskId);
    }

    public TaskComment save(TaskComment taskComment) {
        return taskCommentRepository.save(taskComment);
    }
}
