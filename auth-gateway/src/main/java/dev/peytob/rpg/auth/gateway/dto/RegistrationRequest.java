package dev.peytob.rpg.auth.gateway.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegistrationRequest(
    @NotBlank
    String password,

    @NotBlank
    String username,

    @NotBlank
    @Email
    String email
) {
}
