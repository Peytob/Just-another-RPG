package dev.peytob.rpg.auth.gateway.dto.user;

import dev.peytob.rpg.auth.gateway.dto.NamedIdDto;

import java.time.Instant;
import java.util.Collection;

public record UserGetDto(
    String id,
    Collection<NamedIdDto> groups,
    NamedIdDto realm,
    String username,
    String email,
    Boolean isBlocked,
    Instant lastLoginAt,
    Instant createdAt,
    Instant updatedAt
) {
}
