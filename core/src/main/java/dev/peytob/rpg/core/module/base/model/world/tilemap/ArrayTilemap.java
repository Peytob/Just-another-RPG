package dev.peytob.rpg.core.module.base.model.world.tilemap;

import dev.peytob.rpg.core.module.base.resource.Tile;
import dev.peytob.rpg.math.vector.Vec2i;

import static dev.peytob.rpg.math.vector.Vectors.immutableVec2i;

/**
 * TODO Make memory-optimized quadtree implementation of tilemap.
 */
class ArrayTilemap implements Tilemap {

    private final Vec2i sizes;

    private final PlacedTile[][] map;

    public ArrayTilemap(Vec2i sizes) {
        this.sizes = immutableVec2i(sizes);
        this.map = new PlacedTile[sizes.x()][sizes.y()];
    }

    @Override
    public PlacedTile getTile(int x, int y) {
        return containsCoordinate(x, y) ? map[x][y] : null;
    }

    @Override
    public PlacedTile getTile(Vec2i position) {
        return getTile(position.x(), position.y());
    }

    @Override
    public PlacedTile setTile(int x, int y, Tile tile) {
        PlacedTile alreadyPlacedTile = getTile(x, y);

        if (alreadyPlacedTile != null) {
            return setTile(alreadyPlacedTile.gridPosition(), tile);
        } else {
            return setTile(immutableVec2i(x, y), tile);
        }
    }

    @Override
    public PlacedTile setTile(Vec2i position, Tile tile) {
        if (tile == null) {
            return removeTile(position);
        }

        if (containsCoordinate(position.x(), position.y())) {
            PlacedTile oldTile = map[position.x()][position.y()];
            map[position.x()][position.y()] = new PlacedTile(tile, position);
            return oldTile;
        }

        return null;
    }

    @Override
    public PlacedTile removeTile(int x, int y) {
        PlacedTile placedTile = getTile(x, y);

        if (placedTile != null) {
            map[x][y] = null;
        }

        return placedTile;
    }

    @Override
    public PlacedTile removeTile(Vec2i position) {
        return removeTile(position.x(), position.y());
    }

    @Override
    public Vec2i getSizes() {
        return sizes;
    }

    private boolean containsCoordinate(int x, int y) {
        return 0 <= x && x < getSizes().x() && 0 <= y && y < getSizes().y();
    }
}
