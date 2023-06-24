package dev.peytob.rpg.core.module.level.model.tilemap;

import dev.peytob.rpg.core.module.level.resource.Tile;
import dev.peytob.rpg.math.vector.Vec2i;
import dev.peytob.rpg.math.vector.Vectors;

/**
 * TODO Make memory-optimized quadtree implementation of tilemap.
 */
class ArrayTilemap implements Tilemap {

    private final Vec2i sizes;

    private final Tile[][] map;

    public ArrayTilemap(Vec2i sizes) {
        this.sizes = Vectors.immutableVec2i(sizes);
        this.map = new Tile[sizes.x()][sizes.y()];
    }

    @Override
    public Tile getTile(int x, int y) {
        return containsCoordinate(x, y) ? map[x][y] : null;
    }

    @Override
    public Tile setTile(int x, int y, Tile tile) {
        if (containsCoordinate(x, y)) {
            Tile oldTile = map[x][y];
            map[x][y] = tile;
            return oldTile;
        }

        return null;
    }

    @Override
    public Tile removeTile(int x, int y) {
        return setTile(x, y, null);
    }

    @Override
    public Vec2i getSizes() {
        return sizes;
    }

    private boolean containsCoordinate(int x, int y) {
        return 0 <= x && x < getSizes().x() && 0 <= y && y < getSizes().y();
    }
}
