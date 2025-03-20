package taskmanagement.model.dto.response;

public record TaskCommentDtoResponse(
        String id,
        String task_id,
        String text,
        String author
) {}
