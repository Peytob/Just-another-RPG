package dev.peytob.rpg.auth.gateway.dto.group;

import jakarta.validation.constraints.NotBlank;

public record GroupUpdateDto(
    @NotBlank
    String name
) {
}
