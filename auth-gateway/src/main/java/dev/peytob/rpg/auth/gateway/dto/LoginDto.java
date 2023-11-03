package dev.peytob.rpg.auth.gateway.dto;

import jakarta.validation.constraints.NotEmpty;

public record LoginDto(
    @NotEmpty
    String password,

    @NotEmpty
    String username
) {
}
