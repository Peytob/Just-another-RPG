package dev.peytob.rpg.client.graphic.service;

import dev.peytob.rpg.client.graphic.model.Camera;
import dev.peytob.rpg.client.graphic.model.render.RenderingContext;
import dev.peytob.rpg.client.graphic.model.render.RenderingQueue;
import dev.peytob.rpg.client.graphic.resource.ShaderProgram;
import dev.peytob.rpg.core.gameplay.resource.tilemap.Tilemap;
import dev.peytob.rpg.core.gameplay.resource.tilemap.layer.TilemapLayer;
import dev.peytob.rpg.math.geometry.RectI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static dev.peytob.rpg.math.geometry.Rectangles.rectI;
import static java.lang.Math.max;
import static java.lang.Math.min;

@Service
@RequiredArgsConstructor
public class TilemapRenderService {

    private final RenderService renderService;

    private final SpriteService spriteService;

    private final DefaultShaderProgramsService defaultShaderProgramsService;

    public void renderTilemap(Tilemap tilemap, Camera camera) {
        // TODO Compute from camera
        RectI cullingTilesRect = rectI(0, 0, 500, 500);

        int fromX = max(cullingTilesRect.topLeft().x(), 0);
        int toX = min(cullingTilesRect.bottomRight().x(), tilemap.getLayerSize().x());
        int fromY = max(cullingTilesRect.topLeft().y(), 0);
        int toY = min(cullingTilesRect.bottomRight().y(), tilemap.getLayerSize().y());

        RectI visibleTilesRect = rectI(fromX, fromY, toX - fromX, toY - fromY);

        RenderingQueue renderingQueue = new RenderingQueue();

        for (int layerIndex = 0; layerIndex < tilemap.getLayersCount(); ++layerIndex) {
            TilemapLayer layer = tilemap.getUnmodifiableLayer(layerIndex);
            renderTilemapLayer(layer, visibleTilesRect, renderingQueue);
        }

        ShaderProgram tilemapShaderProgram = defaultShaderProgramsService.getTilemapShaderProgram();

        RenderingContext renderingContext = new RenderingContext();
        renderingContext.setCamera(camera);
        renderingContext.setShaderProgram(tilemapShaderProgram);

        renderService.performRendering(renderingContext, renderingQueue);
    }

    private void renderTilemapLayer(TilemapLayer layer, RectI visibleTilesRect, RenderingQueue renderingQueue) {
        for (int x = visibleTilesRect.topLeft().x(); x < visibleTilesRect.topRight().x(); x++) {
            for (int y = visibleTilesRect.topLeft().y(); y < visibleTilesRect.bottomRight().y(); y++) {
                // TODO Build tilemap rendering query
            }
        }
    }
}
