package dev.peytob.rpg.core.gameplay.resource.tilemap.layer;

import dev.peytob.rpg.math.vector.Vec2i;

public enum TilemapLayers {;

    public static TilemapLayer mutable(Vec2i sizes) {
        if (sizes.x() <= 0 || sizes.y() <= 0) {
            throw new IllegalArgumentException("Tilemap layer sizes should be positive!");
        }

        return new ArrayTilemapLayer(sizes);
    }

    public static TilemapLayer unmodifiable(TilemapLayer tilemapLayer) {
        if (tilemapLayer instanceof UnmodifiableTilemapLayer) {
            return tilemapLayer;
        }

        return new UnmodifiableTilemapLayer(tilemapLayer);
    }
}
