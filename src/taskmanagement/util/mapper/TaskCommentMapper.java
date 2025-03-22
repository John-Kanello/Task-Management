package taskmanagement.util.mapper;

import org.springframework.stereotype.Component;
import taskmanagement.model.dto.response.TaskCommentDtoResponse;
import taskmanagement.model.entity.TaskComment;

@Component
public class TaskCommentMapper implements Mapper<TaskComment, TaskCommentDtoResponse> {

    @Override
    public TaskCommentDtoResponse toDto(TaskComment entity) {
        return new TaskCommentDtoResponse(
                String.valueOf(entity.getId()),
                String.valueOf(entity.getTask().getId()),
                entity.getText(),
                entity.getAuthor()
        );
    }
}
