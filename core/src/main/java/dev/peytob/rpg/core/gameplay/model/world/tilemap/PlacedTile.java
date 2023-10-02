package dev.peytob.rpg.core.gameplay.model.world.tilemap;

import dev.peytob.rpg.core.resource.Tile;
import dev.peytob.rpg.math.vector.Vec2i;

public record PlacedTile(
    Tile tile,
    Vec2i gridPosition
) {
}
