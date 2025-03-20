package taskmanagement.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TaskCommentRequestDto(
        @NotBlank
        String text
) {}
