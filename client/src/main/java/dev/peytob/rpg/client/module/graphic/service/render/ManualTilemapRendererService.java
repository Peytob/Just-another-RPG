package dev.peytob.rpg.client.module.graphic.service.render;

import dev.peytob.rpg.client.module.graphic.model.Camera;
import dev.peytob.rpg.client.module.graphic.service.facade.DefaultShaderProgramsService;
import dev.peytob.rpg.client.module.graphic.service.vendor.RenderService;
import dev.peytob.rpg.core.module.location.model.tilemap.Tilemap;
import org.springframework.stereotype.Component;

// TODO Optimize tilemap rendering process later
@Component
public final class ManualTilemapRendererService implements TilemapRenderingService {

    @Override
    public void renderTilemap(Camera camera, Tilemap tilemap) {

    }
}
