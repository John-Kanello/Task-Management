package taskmanagement.model.dto.request;

import jakarta.validation.constraints.NotNull;

public record TaskStatusDto(
        @NotNull
        TaskStatus status
) {

    public enum TaskStatus {
        CREATED,
        IN_PROGRESS,
        COMPLETED
    }
}