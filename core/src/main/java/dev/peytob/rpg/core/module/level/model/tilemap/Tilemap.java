package dev.peytob.rpg.core.module.level.model.tilemap;

import dev.peytob.rpg.core.module.level.resource.Tile;
import dev.peytob.rpg.math.vector.Vec2i;

public interface Tilemap {

    /**
     * Returns tile on (x, y) coordinates. If tile not exists or given wrong coordinates - returns null.
     */
    PlacedTile getTile(int x, int y);

    PlacedTile getTile(Vec2i position);

    /**
     * Sets tile on (x, y) coordinates. Returns old tile if there was, null otherwise.
     */
    PlacedTile setTile(int x, int y, Tile tile);

    PlacedTile setTile(Vec2i position, Tile tile);

    /**
     * Removes tile on (x, y) coordinates. If tile not exists or given wrong coordinates - returns null.
     */
    PlacedTile removeTile(int x, int y);

    PlacedTile removeTile(Vec2i position);

    Vec2i getSizes();
}
