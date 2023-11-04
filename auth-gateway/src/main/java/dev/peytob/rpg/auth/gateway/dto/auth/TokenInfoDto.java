package dev.peytob.rpg.auth.gateway.dto.auth;

import dev.peytob.rpg.auth.gateway.entity.TokenType;

import java.time.Instant;
import java.util.Collection;

public record TokenInfoDto(
    String realmName,
    String username,
    String email,
    Collection<String> groups,
    Instant tokenExpiredAt,
    TokenType tokenType
) {
}
