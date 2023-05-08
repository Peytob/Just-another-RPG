package dev.peytob.rpg.core.module.location.model.tilemap;

import dev.peytob.rpg.core.module.location.resource.Tile;
import dev.peytob.rpg.math.vector.Vec2i;

public interface Tilemap {

    /**
     * Returns tile on (x, y) coordinates. If tile not exists or given wrong coordinates - returns null.
     */
    Tile getTile(int x, int y);

    default Tile getTile(Vec2i position) {
        return getTile(position.x(), position.y());
    }

    /**
     * Sets tile on (x, y) coordinates. Returns old tile if there was, null otherwise.
     */
    Tile setTile(int x, int y, Tile tile);

    default Tile setTile(Vec2i position, Tile tile) {
        return setTile(position.x(), position.y(), tile);
    }

    /**
     * Removes tile on (x, y) coordinates. If tile not exists or given wrong coordinates - returns null.
     */
    Tile removeTile(int x, int y);

    default Tile removeTile(Vec2i position) {
        return removeTile(position.x(), position.y());
    }

    Vec2i getSizes();
}
