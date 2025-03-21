package taskmanagement.service;

import org.springframework.stereotype.Service;
import taskmanagement.model.dto.request.TaskDetailsRequestDto;
import taskmanagement.model.entity.TaskDetails;
import taskmanagement.repository.TaskDetailsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskDetailsService {
    private final TaskDetailsRepository taskDetailsRepository;

    public TaskDetailsService(TaskDetailsRepository taskDetailsRepository) {
        this.taskDetailsRepository = taskDetailsRepository;
    }

    public Optional<TaskDetails> findById(long id) {
        return taskDetailsRepository.findById(id);
    }

    public List<TaskDetails> findAll() {
        return taskDetailsRepository.findAllOrderByTimestampDescending();
    }

    public List<TaskDetails> findByAuthor(String author) {
        return taskDetailsRepository.findByAuthorOrderByTimestampDescending(author);
    }

    public List<TaskDetails> findByAssignee(String assignee) {
        return taskDetailsRepository.findByAssigneeOrderByTimestampDescending(assignee);
    }

    public List<TaskDetails> findByAuthorAndAssignee(String author, String assignee) {
        return taskDetailsRepository.findByAuthorAndAssigneeOrderByTimestampDescending(author, assignee);
    }

    public boolean existsById(long id) {
        return taskDetailsRepository.existsById(id);
    }

    public TaskDetails save(TaskDetails taskDetails) {
        return taskDetailsRepository.save(taskDetails);
    }

    public TaskDetails save(TaskDetailsRequestDto taskDetailsRequestDto) {
        TaskDetails taskDetails = new TaskDetails(
                taskDetailsRequestDto.getTitle(),
                taskDetailsRequestDto.getDescription(),
                "CREATED",
                LocalDateTime.now(),
                taskDetailsRequestDto.getAuthor(),
                "none"
        );
        return taskDetailsRepository.save(taskDetails);
    }

    public void deleteById(long id) {
        taskDetailsRepository.deleteById(id);
    }
}
