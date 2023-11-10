package dev.peytob.rpg.auth.gateway.dto.realm;

import jakarta.validation.constraints.NotBlank;

public record RealmCreateDto(
    @NotBlank
    String name
) {
}
