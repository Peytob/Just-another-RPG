package dev.peytob.rpg.client.module.graphic.service.render;

import dev.peytob.rpg.client.module.graphic.model.Camera;
import dev.peytob.rpg.client.module.graphic.model.RenderContext;
import dev.peytob.rpg.client.module.graphic.resource.Mesh;
import dev.peytob.rpg.client.module.graphic.resource.ShaderProgram;
import dev.peytob.rpg.client.module.graphic.service.MeshService;
import dev.peytob.rpg.client.module.graphic.service.facade.DefaultMeshesService;
import dev.peytob.rpg.client.module.graphic.service.facade.DefaultShaderProgramsService;
import dev.peytob.rpg.client.module.graphic.service.vendor.RenderService;
import dev.peytob.rpg.core.module.location.model.tilemap.Tilemap;
import dev.peytob.rpg.core.module.location.resource.Tile;
import dev.peytob.rpg.math.vector.Vec2i;
import dev.peytob.rpg.math.vector.Vectors;
import org.lwjgl.BufferUtils;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;

// TODO Optimize tilemap rendering process later
@Component
public final class ManualTilemapRendererService implements TilemapRenderingService {

    private final DefaultShaderProgramsService defaultShaderProgramsService;

    private final MeshService meshService;
    private final RenderService renderService;

    public ManualTilemapRendererService(
        DefaultShaderProgramsService defaultShaderProgramsService,
        MeshService meshService,
        RenderService renderService
    ) {
        this.defaultShaderProgramsService = defaultShaderProgramsService;
        this.meshService = meshService;
        this.renderService = renderService;
    }

    @Override
    public void renderTilemap(Camera camera, Tilemap tilemap) {
        // TODO Dont render all map and make new buffer every method call!

        ByteBuffer tilemapBuffer = makeTilemapBuffer(tilemap, Vectors.immutableVec2i(), tilemap.getSizes());
        int tilesCount = tilemap.getSizes().x() * tilemap.getSizes().y();

        Mesh tilemapMesh = meshService.createMesh(tilemapBuffer, tilesCount, "tilemap");

        ShaderProgram tilemapShaderProgram = defaultShaderProgramsService.getTilemapShaderProgram();

        RenderContext renderContext = new RenderContext();
        renderContext.setRenderMode(GL_POINTS);
        renderContext.setShaderProgramId(tilemapShaderProgram.id());
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
