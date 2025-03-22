package taskmanagement.model.dto.request;

import jakarta.validation.constraints.*;
import taskmanagement.model.Role;

public record RegistrationRequest(
        @NotBlank
        @Email(regexp = ".+[@].+[\\.].+")
        String email,
        @NotBlank
        @Size(min = 6)
        String password,
        @NotNull
        Role role
) {}
