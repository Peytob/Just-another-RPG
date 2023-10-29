package dev.peytob.rpg.auth.gateway.dto;

import java.time.Instant;

public record GroupDto(
    String id,
    NamedIdDto realm,
    String name,
    Instant createdAt,
    Instant updatedAt
) {
}
