package dev.peytob.rpg.backend.dto.auth;

import dev.peytob.rpg.backend.entity.TokenType;
import dev.peytob.rpg.backend.entity.UserRole;

import java.time.Instant;
import java.util.Collection;

public record TokenDto(
    String username,
    Collection<UserRole> roles,
    TokenType type,
    Instant tokenExpiredAt
) {
}
