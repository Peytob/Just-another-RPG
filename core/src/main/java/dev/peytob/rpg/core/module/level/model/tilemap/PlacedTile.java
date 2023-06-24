package dev.peytob.rpg.core.module.level.model.tilemap;

import dev.peytob.rpg.core.module.level.resource.Tile;
import dev.peytob.rpg.math.vector.Vec2i;

public record PlacedTile(
    Tile tile,
    Vec2i gridPosition
) {
}
