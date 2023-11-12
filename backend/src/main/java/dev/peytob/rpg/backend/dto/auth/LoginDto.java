package dev.peytob.rpg.backend.dto.auth;

import jakarta.validation.constraints.NotEmpty;

public record LoginDto(
    @NotEmpty
    String password,

    @NotEmpty
    String username
) {
}
