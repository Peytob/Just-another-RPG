package dev.peytob.rpg.backend.dto.auth;

import java.time.Instant;

public record TokenDto(
    String realmName,
    String username,
    String email,
    Instant tokenExpiredAt
) {
}
