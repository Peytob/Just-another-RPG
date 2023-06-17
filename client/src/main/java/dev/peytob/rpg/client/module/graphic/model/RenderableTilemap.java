package dev.peytob.rpg.client.module.graphic.model;

import dev.peytob.rpg.core.module.location.model.tilemap.Tilemap;
import dev.peytob.rpg.math.geometry.RectI;
import dev.peytob.rpg.math.geometry.Rectangles;
import dev.peytob.rpg.math.vector.Vec2i;

public class RenderableTilemap {

    private TextureAtlas textureAtlas;

    private Vec2i tileSize;

    private RectI cullingTilesRect;

    private Tilemap tilemap;

    public RenderableTilemap(Tilemap tilemap, Vec2i tileSize, TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
        this.tileSize = tileSize;
        this.tilemap = tilemap;
        this.cullingTilesRect = Rectangles.rectI();
    }

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    public void setTextureAtlas(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
    }

    public Vec2i getTileSize() {
        return tileSize;
    }

    public void setTileSize(Vec2i tileSize) {
        this.tileSize = tileSize;
    }

    public RectI getCullingTilesRect() {
        return cullingTilesRect;
    }

    public void setCullingTilesRect(RectI cullingTilesRect) {
        this.cullingTilesRect = cullingTilesRect;
    }

    public Tilemap getTilemap() {
        return tilemap;
    }

    public void setTilemap(Tilemap tilemap) {
        this.tilemap = tilemap;
    }
}
