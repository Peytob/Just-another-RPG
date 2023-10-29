package dev.peytob.rpg.auth.gateway.dto;

import java.time.Instant;

public record RealmDto(
    String id,
    String name,
    Instant createdAt,
    Instant updatedAt
) {
}
