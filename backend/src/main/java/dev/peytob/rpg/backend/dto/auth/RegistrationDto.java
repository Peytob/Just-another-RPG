package dev.peytob.rpg.backend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegistrationDto(
    @NotBlank
    String password,

    @NotBlank
    String username,

    @NotBlank
    @Email
    String email
) {
}
