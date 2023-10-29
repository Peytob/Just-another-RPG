package dev.peytob.rpg.auth.gateway.dto.group;

import jakarta.validation.constraints.NotBlank;

public record GroupCreateDto(
    @NotBlank
    String name
) {
}
