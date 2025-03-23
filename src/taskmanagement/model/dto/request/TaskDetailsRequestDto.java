package taskmanagement.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TaskDetailsRequestDto(
        @NotBlank String title,
        @NotBlank String description
) {}
