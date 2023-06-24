package dev.peytob.rpg.client.module.graphic.context.system.rendering;

import dev.peytob.rpg.client.module.graphic.context.component.CameraComponent;
import dev.peytob.rpg.client.module.graphic.context.component.TilemapTextureAtlasComponent;
import dev.peytob.rpg.client.module.graphic.model.Camera;
import dev.peytob.rpg.client.module.graphic.model.RenderableTilemap;
import dev.peytob.rpg.client.module.graphic.service.render.TilemapRenderingService;
import dev.peytob.rpg.core.module.level.context.component.TilemapComponent;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import dev.peytob.rpg.math.geometry.RectI;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static dev.peytob.rpg.math.geometry.Rectangles.rectI;
import static dev.peytob.rpg.math.vector.Vectors.immutableVec2i;

@Component
public class TilemapRenderSystem implements System {

    private final TilemapRenderingService tilemapRenderingService;

    public TilemapRenderSystem(TilemapRenderingService tilemapRenderingService) {
        this.tilemapRenderingService = tilemapRenderingService;
    }

    @Override
    public void execute(EcsContext context) {
        Optional<TilemapComponent> tilemapComponent =
            context.getSingletonComponentByType(TilemapComponent.class);

        if (tilemapComponent.isEmpty()) {
            return;
        }

        Optional<CameraComponent> cameraComponent =
            context.getSingletonComponentByType(CameraComponent.class);

        if (cameraComponent.isEmpty()) {
            return;
        }

        Optional<TilemapTextureAtlasComponent> textureAtlasComponent =
            context.getSingletonComponentByType(TilemapTextureAtlasComponent.class);

        if (textureAtlasComponent.isEmpty()) {
            return;
        }

        renderTilemap(cameraComponent.get(), tilemapComponent.get(), textureAtlasComponent.get());
    }

    private void renderTilemap(CameraComponent cameraComponent, TilemapComponent tilemapComponent, TilemapTextureAtlasComponent textureAtlasComponent) {
        Camera camera = cameraComponent.getCamera();

        // TODO Make renderable tilemap component and management entities

        RenderableTilemap renderableTilemap = new RenderableTilemap(
            tilemapComponent.getTilemap(),
            immutableVec2i(140, 140),
            textureAtlasComponent.getTextureAtlas());

        renderableTilemap.setCullingTilesRect(computeCullingTilesRect(camera, renderableTilemap));

        tilemapRenderingService.renderTilemap(camera, renderableTilemap);
    }

    private RectI computeCullingTilesRect(Camera camera, RenderableTilemap renderableTilemap) {
        // TODO Temporary returns all possible map
        return rectI(
            immutableVec2i(),
            immutableVec2i(Integer.MAX_VALUE, Integer.MAX_VALUE)
        );
    }
}
