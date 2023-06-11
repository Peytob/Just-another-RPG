package dev.peytob.rpg.client.module.graphic.service.facade;

import dev.peytob.rpg.client.module.graphic.resource.Mesh;
import dev.peytob.rpg.client.module.graphic.resource.VertexArray;
import dev.peytob.rpg.client.module.graphic.service.vendor.MeshService;
import dev.peytob.rpg.core.module.location.model.tilemap.Tilemap;
import dev.peytob.rpg.core.module.location.resource.Tile;
import dev.peytob.rpg.math.vector.Vec2i;
import org.lwjgl.BufferUtils;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;

import static dev.peytob.rpg.core.module.base.constants.PhysicsConstants.TILE_SIZE;
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
    public Mesh buildTilemapMesh(String textId, Tilemap tilemap) {

        // One rectangle -> Two triangles -> Six vertexes.
        int maximalVerticesCount = tilemap.getSizes().x() * tilemap.getSizes().y() * 6;
        int bufferCapacity = maximalVerticesCount * BYTES_PER_VERTEX;
        TilemapMeshBuilder tilemapMeshBuilder = new TilemapMeshBuilder(bufferCapacity, TILE_SIZE);

        for (int x = 0; x < tilemap.getSizes().x(); x++) {
            for (int y = 0; y < tilemap.getSizes().y(); y++) {
                Tile tile = tilemap.getTile(x, y);

//                if (tile != null) {
                    tilemapMeshBuilder.appendTile(x * TILE_SIZE.x(), y * TILE_SIZE.y(), tile);
//                }
            }
        }

        ByteBuffer vboData = tilemapMeshBuilder.build();

        return meshService.createMesh(textId, vboData, tilemapMeshBuilder.getTotalVertices(), SPRITE_BUFFER_ATTRIBUTES);
    }

    private static final class TilemapMeshBuilder {

        private final ByteBuffer buffer;

        private final Vec2i tileSize;

        private int totalVertices;

        public TilemapMeshBuilder(int bufferCapacity, Vec2i tileSize) {
            this.buffer = BufferUtils.createByteBuffer(bufferCapacity);
            this.tileSize = tileSize;
            this.totalVertices = 0;
        }

        public void appendTile(float positionX, float positionY, Tile tile) {
            appendVertex(positionX, positionY);
            appendVertex(positionX + tileSize.x(), positionY);
            appendVertex(positionX, positionY + tileSize.y());

            appendVertex(positionX + tileSize.x(), positionY);
            appendVertex(positionX, positionY + tileSize.y());
            appendVertex(positionX + tileSize.y(), positionY + tileSize.y());
        }

        public ByteBuffer build() {
            return buffer.flip();
        }

        public int getTotalVertices() {
            return totalVertices;
        }

        private void appendVertex(float positionX, float positionY) {
            this.totalVertices += 1;
            buffer
                .putFloat(positionX).putFloat(positionY);
        }
    }
}
