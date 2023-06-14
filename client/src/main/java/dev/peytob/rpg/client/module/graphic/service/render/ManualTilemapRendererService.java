package dev.peytob.rpg.client.module.graphic.service.render;

import dev.peytob.rpg.client.module.graphic.model.Camera;
import dev.peytob.rpg.client.module.graphic.model.RenderContext;
import dev.peytob.rpg.client.module.graphic.model.TextureAtlas;
import dev.peytob.rpg.client.module.graphic.resource.Mesh;
import dev.peytob.rpg.client.module.graphic.resource.ShaderProgram;
import dev.peytob.rpg.client.module.graphic.service.facade.DefaultShaderProgramsService;
import dev.peytob.rpg.client.module.graphic.service.facade.TilemapMeshService;
import dev.peytob.rpg.client.module.graphic.service.vendor.MeshService;
import dev.peytob.rpg.client.module.graphic.service.vendor.RenderService;
import dev.peytob.rpg.core.module.location.model.tilemap.Tilemap;
import dev.peytob.rpg.math.geometry.RectI;
import dev.peytob.rpg.math.vector.Vec2;
import dev.peytob.rpg.math.vector.Vec2i;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.core.module.base.constants.PhysicsConstants.TILE_SIZE;
import static dev.peytob.rpg.math.geometry.Rectangles.rectI;
import static dev.peytob.rpg.math.vector.Vectors.immutableVec2i;
import static org.lwjgl.opengl.GL11.*;

@Component
public final class ManualTilemapRendererService implements TilemapRenderingService {

    private final TilemapMeshService tilemapMeshService;

    private final MeshService meshService;

    private final RenderService renderService;

    private final DefaultShaderProgramsService defaultShaderProgramsService;

    public ManualTilemapRendererService(TilemapMeshService tilemapMeshService, MeshService meshService, DefaultShaderProgramsService defaultShaderProgramsService, RenderService renderService) {
        this.tilemapMeshService = tilemapMeshService;
        this.meshService = meshService;
        this.renderService = renderService;
        this.defaultShaderProgramsService = defaultShaderProgramsService;
    }

    @Override
    public void renderTilemap(Camera camera, Tilemap tilemap, TextureAtlas textureAtlas) {
        RectI cullingTilesRect = cimputeCullingTilesRect(camera, TILE_SIZE);
        Mesh mesh = tilemapMeshService.buildTilemapMesh("frame_rendering_tilemap", tilemap, textureAtlas, cullingTilesRect);

        ShaderProgram tilemapShaderProgram = defaultShaderProgramsService.getTilemapShaderProgram();

        RenderContext renderContext = new RenderContext();
        renderContext.setRenderMode(GL_TRIANGLES);
        renderContext.setShaderProgramId(tilemapShaderProgram.id());

        renderService.renderMesh(mesh, renderContext);

        meshService.removeMesh(mesh);
    }

    private RectI cimputeCullingTilesRect(Camera camera, Vec2i renderingTileSize) {
        Vec2 topLeft = camera.getVisionRectangle().topLeft().division(renderingTileSize);
        Vec2 sizes = camera.getVisionRectangle().size().division(renderingTileSize).plus(immutableVec2i(2, 2));

        return rectI(
            immutableVec2i(topLeft),
            immutableVec2i(sizes)
        );
    }
}
