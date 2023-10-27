package dev.peytob.rpg.auth.gateway.dto;

import dev.peytob.rpg.auth.gateway.entity.TokenType;

import java.time.Instant;
import java.util.Collection;

/**
 * Token validation DTO. Only for public API use.
 */
public record TokenInfoDto(
    String realmName,
    String username,
    String email,
    Collection<String> groups,
    Instant tokenExpiredAt,
    TokenType tokenType
) {
}
