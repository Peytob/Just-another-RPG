package dev.peytob.rpg.core.gameplay.resource.tilemap.layer;

import dev.peytob.rpg.core.gameplay.resource.tilemap.PlacedTile;
import dev.peytob.rpg.core.gameplay.resource.Tile;
import dev.peytob.rpg.math.vector.Vec2i;

import static dev.peytob.rpg.math.coordinate.CoordinatesUtils.isCoordinatesInRectI;
import static dev.peytob.rpg.math.vector.Vectors.immutableVec2i;

/**
 * TODO Make memory-optimized quadtree implementation of tilemap.
 */
class ArrayTilemapLayer implements TilemapLayer {

    private final Vec2i sizes;

    private final PlacedTile[][] map;

    ArrayTilemapLayer(Vec2i sizes) {
        this.sizes = immutableVec2i(sizes);
        this.map = new PlacedTile[sizes.x()][sizes.y()];
    }

    @Override
    public PlacedTile getTile(int x, int y) {
        return isCoordinatesInRectI(x, y, sizes) ? map[x][y] : null;
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

        if (isCoordinatesInRectI(position, sizes)) {
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
}
