package dev.peytob.rpg.auth.gateway.dto;

import java.time.Instant;
import java.util.Collection;

public record UserDto(
    String id,
    Collection<NamedIdDto> groups,
    NamedIdDto realm,
    String username,
    String email,
    Boolean isBlocked,
    Instant createdAt,
    Instant updatedAt
) {
}
