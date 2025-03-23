package taskmanagement.model.dto.request;

import jakarta.validation.constraints.NotNull;
import taskmanagement.model.TaskStatus;

public record TaskStatusDto(
        @NotNull
        TaskStatus status
) { }