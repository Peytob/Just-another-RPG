package dev.peytob.rpg.client.module.graphic.service.render;

import dev.peytob.rpg.client.module.graphic.model.Camera;
import dev.peytob.rpg.client.module.graphic.model.RenderContext;
import dev.peytob.rpg.client.module.graphic.repository.TextureRepository;
import dev.peytob.rpg.client.module.graphic.resource.Mesh;
import dev.peytob.rpg.client.module.graphic.resource.ShaderProgram;
import dev.peytob.rpg.client.module.graphic.resource.Texture;
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

// TODO Optimize tilemap rendering process later
@Component
public final class ManualTilemapRendererService implements TilemapRenderingService {

    private final MeshService meshService;
    private final RenderService renderService;
    private final TilemapShaderProgramFacade tilemapShaderProgramFacade;
    private final TextureRepository textureRepository;

    public ManualTilemapRendererService(MeshService meshService,
                                        RenderService renderService,
                                        TilemapShaderProgramFacade tilemapShaderProgramFacade,
                                        TextureRepository textureRepository) {
        this.meshService = meshService;
        this.renderService = renderService;
        this.tilemapShaderProgramFacade = tilemapShaderProgramFacade;
        this.textureRepository = textureRepository;
    }

    @Override
    public void renderTilemap(Camera camera, Tilemap tilemap) {
        // TODO Dont render all map and make new buffer every method call!

        ByteBuffer tilemapBuffer = makeTilemapBuffer(tilemap, Vectors.immutableVec2i(), tilemap.getSizes());
        int tilesCount = tilemap.getSizes().x() * tilemap.getSizes().y();

        Mesh tilemapMesh = meshService.createMesh(tilemapBuffer, tilesCount, "tilemap");

        ShaderProgram tilemapShaderProgram = tilemapShaderProgramFacade.getTilemapShaderProgram();

        Texture textureAtlas = textureRepository.getById(DEFAULT_TILE_ATLAS_ID);

        RenderContext renderContext = new RenderContext();
        renderContext.setRenderMode(GL_POINTS);
        renderContext.setShaderProgramId(tilemapShaderProgram.id());
        renderContext.setTextureId(textureAtlas.id());
        renderService.renderMesh(tilemapMesh, renderContext);

        meshService.removeMesh(tilemapMesh);
    }

    private ByteBuffer makeTilemapBuffer(Tilemap tilemap, Vec2i from, Vec2i to) {
        Vec2i area = from.plus(to);

        ByteBuffer meshBuffer = ByteBuffer.allocate(area.x() * area.y() * Integer.BYTES);

        for (int x = from.x(); x < to.x(); ++x) {
            for (int y = from.y(); y < to.y(); ++y) {
                Tile tile = tilemap.getTile(x, y);

                if (tile == null) {
                    meshBuffer.putInt(0);
                } else {
                    meshBuffer.putInt(tile.id());
                }
            }
        }

        return meshBuffer;
    }
}
