package taskmanagement.model.dto.request;

import jakarta.validation.constraints.*;

public record RegistrationRequest(
        @NotBlank
        @Email(regexp = ".+[@].+[\\.].+")
        String email,
        @NotBlank
        @Size(min = 6)
        String password
) {}
