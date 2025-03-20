package taskmanagement.util;

import org.springframework.stereotype.Component;
import taskmanagement.model.dto.response.TaskDetailsCommentsResponseDto;
import taskmanagement.model.entity.TaskDetails;

@Component
public class TaskDetailsCommentsMapper implements Mapper<TaskDetails, TaskDetailsCommentsResponseDto> {

    @Override
    public TaskDetailsCommentsResponseDto toDto(TaskDetails entity) {
        return new TaskDetailsCommentsResponseDto(
                String.valueOf(entity.getId()),
                entity.getTitle(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getAuthor().getEmail(),
                entity.getAssignee(),
                entity.getComments() == null ? 0 : entity.getComments().size()
        );
    }
}
