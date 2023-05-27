package dev.peytob.rpg.client.module.graphic.model;

import dev.peytob.rpg.core.module.location.model.tilemap.Tilemap;
import dev.peytob.rpg.math.vector.Vec2;
import dev.peytob.rpg.math.vector.Vec2i;

public final class RenderableTilemap {

    private final Tilemap tilemap;

    // TODO Make separated chunks
    private final TilemapRenderChunk tilemapRenderChunk;

    private final Vec2i chunksSizes;

    public RenderableTilemap(Tilemap tilemap, TilemapRenderChunk tilemapRenderChunk, Vec2i chunksSizes, ) {
        this.tilemap = tilemap;
        this.tilemapRenderChunk = tilemapRenderChunk;
        this.chunksSizes = chunksSizes;
    }

    public Tilemap getTilemap() {
        return tilemap;
    }

    public TilemapRenderChunk getTilemapRenderChunk(Vec2i tilemapCoordinates) {

        return ;
    }
}
