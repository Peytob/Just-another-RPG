package dev.peytob.rpg.client.module.graphic.service.facade;

import dev.peytob.rpg.client.module.graphic.resource.Mesh;
import dev.peytob.rpg.client.module.graphic.resource.VertexArray;
import dev.peytob.rpg.client.module.graphic.service.vendor.MeshService;
import dev.peytob.rpg.core.module.location.model.tilemap.Tilemap;
import org.lwjgl.BufferUtils;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;

@Component
public class TilemapMeshServiceImpl implements TilemapMeshService {

    /**
     * Target shader layout:
     * layout (location = 0) in vec2 a_tilePosition;
     * layout (location = 1) in vec2 a_normalizedTextureCoordinates;
     */
    private static final Collection<VertexArray.VertexArrayAttribute> SPRITE_BUFFER_ATTRIBUTES = List.of(
        new VertexArray.VertexArrayAttribute(0, 2, GL_FLOAT, false, 2 * Float.BYTES, 0L));

    private static final int BYTES_PER_VERTEX = 2 * Float.BYTES;

    private final MeshService meshService;

    public TilemapMeshServiceImpl(MeshService meshService) {
        this.meshService = meshService;
    }

    @Override
    public Mesh buildTilemapMesh(Tilemap tilemap) {
        // Just creates triangle

        ByteBuffer justTriangle = BufferUtils.createByteBuffer(3 * BYTES_PER_VERTEX);
        justTriangle.putFloat(0.0f).putFloat(0.5f);
        justTriangle.putFloat(0.5f).putFloat(0.0f);
        justTriangle.putFloat(-0.5f).putFloat(0.0f);

        return meshService.createMesh("test_triangle", justTriangle.flip(), 3, SPRITE_BUFFER_ATTRIBUTES);
    }
}
