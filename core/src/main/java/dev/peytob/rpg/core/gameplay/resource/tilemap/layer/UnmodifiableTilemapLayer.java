package dev.peytob.rpg.core.gameplay.resource.tilemap.layer;

import dev.peytob.rpg.core.gameplay.resource.tilemap.PlacedTile;
import dev.peytob.rpg.core.gameplay.resource.Tile;
import dev.peytob.rpg.math.vector.Vec2i;

final class UnmodifiableTilemapLayer implements TilemapLayer {

    private final TilemapLayer tilemapLayer;

    public UnmodifiableTilemapLayer(TilemapLayer tilemapLayer) {
        this.tilemapLayer = tilemapLayer;
    }

    @Override
    public PlacedTile getTile(int x, int y) {
        return tilemapLayer.getTile(x, y);
    }

    @Override
    public PlacedTile getTile(Vec2i position) {
        return tilemapLayer.getTile(position);
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
        return tilemapLayer.getSizes();
    }
}
