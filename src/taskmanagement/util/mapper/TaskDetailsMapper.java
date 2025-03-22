package taskmanagement.util.mapper;

import org.springframework.stereotype.Component;
import taskmanagement.model.dto.response.TaskDetailsResponseDto;
import taskmanagement.model.entity.TaskDetails;

@Component
public class TaskDetailsMapper implements Mapper<TaskDetails, TaskDetailsResponseDto> {

    @Override
    public TaskDetailsResponseDto toDto(TaskDetails entity) {
        return new TaskDetailsResponseDto(
                String.valueOf(entity.getId()),
                entity.getTitle(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getAuthor().getEmail(),
                entity.getAssignee()
        );
    }
}
