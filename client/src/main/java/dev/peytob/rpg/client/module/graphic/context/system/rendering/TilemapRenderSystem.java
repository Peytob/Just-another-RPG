package dev.peytob.rpg.client.module.graphic.context.system.rendering;

import dev.peytob.rpg.client.module.graphic.context.component.CameraComponent;
import dev.peytob.rpg.core.module.location.context.component.TilemapComponent;
import dev.peytob.rpg.client.module.graphic.model.Camera;
import dev.peytob.rpg.client.module.graphic.service.render.TilemapRenderingService;
import dev.peytob.rpg.core.module.location.model.tilemap.Tilemap;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TilemapRenderSystem implements System {

    private final TilemapRenderingService tilemapRenderingService;

    public TilemapRenderSystem(TilemapRenderingService tilemapRenderingService) {
        this.tilemapRenderingService = tilemapRenderingService;
    }

    @Override
    public void execute(EcsContext context) {
        Optional<TilemapComponent> tilemapComponent = context.getSingletonComponentByType(TilemapComponent.class);

        if (tilemapComponent.isEmpty()) {
            return;
        }

        Optional<CameraComponent> cameraComponent = context.getSingletonComponentByType(CameraComponent.class);

        if (cameraComponent.isEmpty()) {
            return;
        }

        renderTilemap(cameraComponent.get(), tilemapComponent.get());
    }

    private void renderTilemap(CameraComponent cameraComponent, TilemapComponent tilemapComponent) {
        Camera camera = cameraComponent.getCamera();
        Tilemap tilemap = tilemapComponent.getTilemap();

        tilemapRenderingService.renderTilemap(camera, tilemap);
    }
}
