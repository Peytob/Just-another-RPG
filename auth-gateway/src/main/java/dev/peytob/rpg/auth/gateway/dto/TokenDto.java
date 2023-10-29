package dev.peytob.rpg.auth.gateway.dto;

import dev.peytob.rpg.auth.gateway.entity.TokenType;

import java.time.Instant;

public record TokenDto(
    String id,
    Instant expirationAt,
    TokenType type,
    NamedIdDto user,
    Instant createdAt,
    Instant updatedAt
) {
}
