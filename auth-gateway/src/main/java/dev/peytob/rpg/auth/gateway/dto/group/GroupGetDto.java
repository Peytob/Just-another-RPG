package dev.peytob.rpg.auth.gateway.dto.group;

import dev.peytob.rpg.auth.gateway.dto.NamedIdDto;

import java.time.Instant;

public record GroupGetDto(
    String id,
    NamedIdDto realm,
    String name,
    Instant createdAt,
    Instant updatedAt
) {
}
