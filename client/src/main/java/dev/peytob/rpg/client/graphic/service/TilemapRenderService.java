package dev.peytob.rpg.client.graphic.service;

import dev.peytob.rpg.client.graphic.model.Camera;
import dev.peytob.rpg.client.graphic.model.RenderContext;
import dev.peytob.rpg.core.gameplay.resource.tilemap.Tilemap;
import dev.peytob.rpg.core.gameplay.resource.tilemap.layer.TilemapLayer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TilemapRenderService {

    private final RenderService renderService;

    private final SpriteService spriteService;

    public void renderTilemap(Tilemap tilemap, RenderContext renderContext) {
        for (int layerIndex = 0; layerIndex < tilemap.getLayersCount(); ++layerIndex) {
            renderTilemapLayer(tilemap.getUnmodifiableLayer(layerIndex), renderContext.getCamera());
        }
    }

    private void renderTilemapLayer(TilemapLayer layer, Camera camera) {

    }
}
