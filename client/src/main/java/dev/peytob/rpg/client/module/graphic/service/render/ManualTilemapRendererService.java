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
import org.springframework.stereotype.Component;

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
        Mesh mesh = tilemapMeshService.buildTilemapMesh("frame_rendering_tilemap", tilemap, textureAtlas);

        ShaderProgram tilemapShaderProgram = defaultShaderProgramsService.getTilemapShaderProgram();

        RenderContext renderContext = new RenderContext();
        renderContext.setRenderMode(GL_TRIANGLES);
        renderContext.setShaderProgramId(tilemapShaderProgram.id());

        renderService.renderMesh(mesh, renderContext);

        meshService.removeMesh(mesh);
    }
}
