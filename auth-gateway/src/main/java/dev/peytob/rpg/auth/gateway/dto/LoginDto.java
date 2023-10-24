package dev.peytob.rpg.auth.gateway.dto;

import dev.peytob.rpg.auth.gateway.entity.TokenType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LoginDto(
    @NotEmpty
    String password,

    @NotEmpty
    String username,

    @NotNull
    @Schema(description = "Choose 'Session' token to generate short-time tokens, or 'Longtime' to generate long-time token")
    TokenType tokenType
) {
}
