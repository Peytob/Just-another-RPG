package dev.peytob.rpg.auth.gateway.dto.realm;

import java.time.Instant;

public record RealmGetDto(
    String id,
    String name,
    Instant createdAt,
    Instant updatedAt
) {
}
