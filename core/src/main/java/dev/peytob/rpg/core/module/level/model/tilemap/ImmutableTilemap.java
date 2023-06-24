package dev.peytob.rpg.core.module.level.model.tilemap;

import dev.peytob.rpg.core.module.level.resource.Tile;
import dev.peytob.rpg.math.vector.Vec2i;

final class ImmutableTilemap implements Tilemap {

    private final Tilemap tilemap;

    public ImmutableTilemap(Tilemap tilemap) {
        this.tilemap = tilemap;
    }

    @Override
    public Tile getTile(int x, int y) {
        return tilemap.getTile(x, y);
    }

    @Override
    public Tile getTile(Vec2i position) {
        return tilemap.getTile(position);
    }

    @Override
    public Vec2i getSizes() {
        return tilemap.getSizes();
    }

    @Override
    public Tile setTile(int x, int y, Tile tile) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Tile removeTile(int x, int y) {
        throw new UnsupportedOperationException();
    }
}
