package taskmanagement.model.dto.response;

public record TaskDetailsResponseDto(
        String id,
        String title,
        String description,
        String status,
        String author,
        String assignee
) {}
