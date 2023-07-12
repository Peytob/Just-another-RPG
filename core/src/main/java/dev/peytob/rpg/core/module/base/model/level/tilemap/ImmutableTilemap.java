package dev.peytob.rpg.core.module.base.model.level.tilemap;

import dev.peytob.rpg.core.module.base.resource.Tile;
import dev.peytob.rpg.math.vector.Vec2i;

final class ImmutableTilemap implements Tilemap {

    private final Tilemap tilemap;

    public ImmutableTilemap(Tilemap tilemap) {
        this.tilemap = tilemap;
    }

    @Override
    public PlacedTile getTile(int x, int y) {
        return tilemap.getTile(x, y);
    }

    @Override
    public PlacedTile getTile(Vec2i position) {
        return tilemap.getTile(position);
    }

    @Override
    public PlacedTile setTile(int x, int y, Tile tile) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PlacedTile setTile(Vec2i position, Tile tile) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PlacedTile removeTile(int x, int y) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PlacedTile removeTile(Vec2i position) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Vec2i getSizes() {
        return tilemap.getSizes();
    }
}
