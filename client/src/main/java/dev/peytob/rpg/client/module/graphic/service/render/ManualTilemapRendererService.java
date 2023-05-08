package dev.peytob.rpg.client.module.graphic.service.render;

import dev.peytob.rpg.client.module.graphic.model.Camera;
import dev.peytob.rpg.client.module.graphic.model.RenderContext;
import dev.peytob.rpg.client.module.graphic.resource.Mesh;
import dev.peytob.rpg.client.module.graphic.resource.ShaderProgram;
import dev.peytob.rpg.client.module.graphic.service.facade.DefaultMeshesService;
import dev.peytob.rpg.client.module.graphic.service.facade.DefaultShaderProgramsService;
import dev.peytob.rpg.client.module.graphic.service.vendor.RenderService;
import dev.peytob.rpg.core.module.location.model.tilemap.Tilemap;
import dev.peytob.rpg.math.vector.Vec2i;
import dev.peytob.rpg.math.vector.Vectors;
import org.springframework.stereotype.Component;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

// TODO Optimize tilemap rendering process later
@Component
public final class ManualTilemapRendererService implements TilemapRenderingService {

    private final DefaultShaderProgramsService defaultShaderProgramsService;

    private final DefaultMeshesService defaultMeshesService;

    private final RenderService renderService;

    public ManualTilemapRendererService(
        DefaultShaderProgramsService defaultShaderProgramsService,
        DefaultMeshesService defaultMeshesService,
        RenderService renderService
    ) {
        this.defaultShaderProgramsService = defaultShaderProgramsService;
        this.defaultMeshesService = defaultMeshesService;
        this.renderService = renderService;
    }

    @Override
    public void renderTilemap(Camera camera, Tilemap tilemap) {
        ShaderProgram tilemapShaderProgram = defaultShaderProgramsService.getTilemapShaderProgram();
        Mesh tileMesh = defaultMeshesService.getTileMesh();

        RenderContext renderContext = new RenderContext();
        renderContext.setShaderProgramId(tilemapShaderProgram.id());
        renderContext.setRenderMode(GL_TRIANGLES);

        Vec2i from = Vectors.immutableVec2i(0, 0);
        Vec2i to = tilemap.getSizes();

        for (int x = from.x(); x < to.x(); x++) {
            for (int y = from.y(); y < to.y(); y++) {
                renderService.renderMesh(tileMesh, renderContext);
            }
        }
    }
}
