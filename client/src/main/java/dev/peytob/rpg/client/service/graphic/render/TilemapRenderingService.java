package dev.peytob.rpg.client.service.graphic.render;

import dev.peytob.rpg.client.model.graphic.Camera;
import dev.peytob.rpg.core.module.location.model.tilemap.Tilemap;

public interface TilemapRenderingService {

    void renderTilemap(Camera camera, Tilemap tilemap);
}
