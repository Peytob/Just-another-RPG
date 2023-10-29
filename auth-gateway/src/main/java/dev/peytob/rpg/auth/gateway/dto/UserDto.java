package dev.peytob.rpg.auth.gateway.dto;

import java.time.Instant;
import java.util.Collection;

public record UserDto(
    String id,
    Collection<NamedIdDto> groups,
    RealmDto realm,
    String username,
    String email,
    Boolean isActivated,
    Instant createdAt,
    Instant updatedAt
) {
}
