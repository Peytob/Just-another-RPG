package dev.peytob.rpg.auth.gateway.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Collection;

public record UserCreateDto(
    @NotBlank
    String username,

    // TODO Create password pattern
    @NotBlank
    @Size(min = 6, max = 32)
    String password,

    @NotBlank
    @Email
    String email,

    @NotNull
    Collection<String> groups
) {
}
