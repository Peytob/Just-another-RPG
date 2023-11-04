package dev.peytob.rpg.auth.gateway.dto.auth;

import jakarta.validation.constraints.NotEmpty;

public record LoginDto(
    @NotEmpty
    String password,

    @NotEmpty
    String username
) {
}
