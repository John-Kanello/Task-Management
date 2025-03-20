package taskmanagement.exception;

import java.time.LocalDateTime;

public record CustomErrorMessage(
    int statusCode,
    String message,
    String description,
    LocalDateTime timestamp
) {}
