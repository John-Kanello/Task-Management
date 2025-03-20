package taskmanagement.model.dto.response;

public record TaskDetailsCommentsResponseDto(
        String id,
        String title,
        String description,
        String status,
        String author,
        String assignee,
        int total_comments
) {}
