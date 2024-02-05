package dev.peytob.rpg.core.network.model.server;

import dev.peytob.rpg.math.vector.Vec2;
import lombok.Builder;

@Builder
public record WorldState(
    LoadedWorld loadedWorld,
    CharacterInfo characterInfo
) {

    @Builder
    public record LoadedWorld(
        String id
    ) {
    }

    @Builder
    public record CharacterInfo(
        String id,
        String name,
        Vec2 position
    ) {
    }
}
