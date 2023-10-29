package dev.peytob.rpg.auth.gateway.dto;

import dev.peytob.rpg.auth.gateway.dto.realm.RealmGetDto;

import java.time.Instant;
import java.util.Collection;

public record UserDto(
    String id,
    Collection<NamedIdDto> groups,
    RealmGetDto realm,
    String username,
    String email,
    Boolean isActivated,
    Instant createdAt,
    Instant updatedAt
) {
}
