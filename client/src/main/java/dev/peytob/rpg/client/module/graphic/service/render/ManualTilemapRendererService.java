package dev.peytob.rpg.client.module.graphic.service.render;

import dev.peytob.rpg.client.module.graphic.model.Camera;
import dev.peytob.rpg.client.module.graphic.model.RenderContext;
import dev.peytob.rpg.client.module.graphic.repository.TextureAtlasRepository;
import dev.peytob.rpg.client.module.graphic.repository.TextureRepository;
import dev.peytob.rpg.client.module.graphic.resource.Mesh;
import dev.peytob.rpg.client.module.graphic.resource.ShaderProgram;
import dev.peytob.rpg.client.module.graphic.resource.Texture;
import dev.peytob.rpg.client.module.graphic.resource.TextureAtlas;
import dev.peytob.rpg.client.module.graphic.service.MeshService;
import dev.peytob.rpg.client.module.graphic.service.facade.shaderprogram.TilemapShaderProgramFacade;
import dev.peytob.rpg.client.module.graphic.service.vendor.RenderService;
import dev.peytob.rpg.core.module.location.model.tilemap.Tilemap;
import dev.peytob.rpg.core.module.location.resource.Tile;
import dev.peytob.rpg.math.vector.Vec2i;
import dev.peytob.rpg.math.vector.Vectors;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;

import static dev.peytob.rpg.client.module.graphic.model.DefaultTexturesIds.DEFAULT_TILE_ATLAS_ID;
import static org.lwjgl.opengl.GL32.*;

@Component
public final class ManualTilemapRendererService implements TilemapRenderingService {

    private final MeshService meshService;
    private final RenderService renderService;
    private final TilemapShaderProgramFacade tilemapShaderProgramFacade;
    private final TextureAtlasRepository textureAtlasRepository;

    public ManualTilemapRendererService(MeshService meshService,
                                        RenderService renderService,
                                        TilemapShaderProgramFacade tilemapShaderProgramFacade,
                                        TextureAtlasRepository textureAtlasRepository) {
        this.meshService = meshService;
        this.renderService = renderService;
        this.tilemapShaderProgramFacade = tilemapShaderProgramFacade;
        this.textureAtlasRepository = textureAtlasRepository;
    }

    @Override
    public void renderTilemap(Camera camera, Tilemap tilemap) {
        // TODO Dont render all map and make new buffer every method call!

//        TextureAtlas textureAtlas = textureAtlasRepository.getById(DEFAULT_TILE_ATLAS_ID);

//        Mesh tilemapMesh = meshService.createMesh(tilemapBuffer, tilesCount, "tilemap");
//
//        ShaderProgram tilemapShaderProgram = tilemapShaderProgramFacade.getTilemapShaderProgram();
//
//        RenderContext renderContext = new RenderContext();
//        renderContext.setRenderMode(GL_TRIANGLES);
//        renderContext.setShaderProgramId(tilemapShaderProgram.id());
//        renderContext.setTextureId(textureAtlas.texture().id());
//        renderService.renderMesh(tilemapMesh, renderContext);
//
//        meshService.removeMesh(tilemapMesh);
    }
}
