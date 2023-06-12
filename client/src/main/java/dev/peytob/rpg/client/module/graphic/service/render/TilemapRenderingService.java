package dev.peytob.rpg.client.module.graphic.service.render;

import dev.peytob.rpg.client.module.graphic.model.Camera;
import dev.peytob.rpg.client.module.graphic.model.TextureAtlas;
import dev.peytob.rpg.core.module.location.model.tilemap.Tilemap;

public interface TilemapRenderingService {

    void renderTilemap(Camera camera, Tilemap tilemap, TextureAtlas textureAtlas);
}
