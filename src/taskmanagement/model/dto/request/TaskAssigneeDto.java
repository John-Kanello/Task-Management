package taskmanagement.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import taskmanagement.model.annotation.EmailOrNone;

public record TaskAssigneeDto(
        @NotBlank
        @EmailOrNone
        String assignee
) {}
