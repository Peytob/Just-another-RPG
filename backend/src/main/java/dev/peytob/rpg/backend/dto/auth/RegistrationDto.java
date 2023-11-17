package dev.peytob.rpg.backend.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static dev.peytob.rpg.backend.configuration.SecurityConfiguration.PASSWORD_PATTERN_STRING;

public record RegistrationDto(
    @NotBlank
    @Pattern(regexp = PASSWORD_PATTERN_STRING)
    @Schema(example = "pAssW0rd!")
    String password,

    @NotBlank
    String username,

    @NotBlank
    @Email
    @Schema(example = "example@mail.dev")
    String email
) {
}
