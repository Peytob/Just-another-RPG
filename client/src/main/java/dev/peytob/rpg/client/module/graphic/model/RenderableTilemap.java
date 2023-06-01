package dev.peytob.rpg.client.module.graphic.model;

import dev.peytob.rpg.core.module.location.model.tilemap.Tilemap;
import dev.peytob.rpg.math.vector.Vec2i;

public final class RenderableTilemap {

    private final Tilemap tilemap;

    private Vec2i renderingTileSize;

    public RenderableTilemap(Tilemap tilemap, Vec2i renderingTileSize) {
        this.tilemap = tilemap;
        this.renderingTileSize = renderingTileSize;
    }

    public Tilemap getTilemap() {
        return tilemap;
    }

    public Vec2i getRenderingTileSize() {
        return renderingTileSize;
    }

    public void setRenderingTileSize(Vec2i renderingTileSize) {
        this.renderingTileSize = renderingTileSize;
    }
}
