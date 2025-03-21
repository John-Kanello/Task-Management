package taskmanagement.service;

import org.springframework.stereotype.Service;
import taskmanagement.model.entity.TaskComment;
import taskmanagement.repository.TaskCommentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskCommentService {
    private final TaskCommentRepository taskCommentRepository;

    public TaskCommentService(TaskCommentRepository taskCommentRepository) {
        this.taskCommentRepository = taskCommentRepository;
    }

    public Optional<TaskComment> findById(long id) {
        return taskCommentRepository.findById(id);
    }

    public boolean existsById(long id) {
        return taskCommentRepository.existsById(id);
    }

    public List<TaskComment> findByTaskId(long taskId) {
        return taskCommentRepository.findByTaskIdOrderByTimestampDescending(taskId);
    }

    public TaskComment save(TaskComment taskComment) {
        return taskCommentRepository.save(taskComment);
    }

    public void deleteById(long id) {
        taskCommentRepository.deleteById(id);
    }
}
