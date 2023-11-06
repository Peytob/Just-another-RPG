package dev.peytob.rpg.auth.gateway.dto.token;

import dev.peytob.rpg.auth.gateway.dto.NamedIdDto;
import dev.peytob.rpg.auth.gateway.entity.TokenType;

import java.time.Instant;

public record TokenGetDto(
    String id,
    Instant expirationAt,
    TokenType type,
    NamedIdDto user,
    Instant createdAt,
    Instant updatedAt
) {
}
