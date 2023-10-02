package dev.peytob.rpg.core.gameplay.model.world;

import dev.peytob.rpg.core.gameplay.model.world.tilemap.layer.TilemapLayer;

public record World(
    TilemapLayer tilemapLayer
) {
}
