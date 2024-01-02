package dev.peytob.rpg.backend.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CharacterGetDto(
    String id,

    String userId,

    @NotBlank
    @Size(min = 6)
    String name
) {
}
