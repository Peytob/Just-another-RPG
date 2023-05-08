package dev.peytob.rpg.core.module.location.model.tilemap;

import dev.peytob.rpg.math.vector.Vec2i;

public enum Tilemaps {;

    public static Tilemap mutable(Vec2i sizes) {
        return new ArrayTilemap(sizes);
    }

    public static Tilemap immutable(Tilemap tilemap) {
        if (tilemap instanceof ImmutableTilemap) {
            return tilemap;
        }

        return new ImmutableTilemap(tilemap);
    }
}
