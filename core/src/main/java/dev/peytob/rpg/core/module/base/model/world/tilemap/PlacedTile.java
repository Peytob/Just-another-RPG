package dev.peytob.rpg.core.module.base.model.world.tilemap;

import dev.peytob.rpg.core.module.base.resource.Tile;
import dev.peytob.rpg.math.vector.Vec2i;

public record PlacedTile(
    Tile tile,
    Vec2i gridPosition
) {
}
