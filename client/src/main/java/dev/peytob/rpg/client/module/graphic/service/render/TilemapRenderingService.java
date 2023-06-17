package dev.peytob.rpg.client.module.graphic.service.render;

import dev.peytob.rpg.client.module.graphic.model.Camera;
import dev.peytob.rpg.client.module.graphic.model.RenderableTilemap;

public interface TilemapRenderingService {

    void renderTilemap(Camera camera, RenderableTilemap renderableTilemap);
}
