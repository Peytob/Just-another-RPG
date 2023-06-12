package dev.peytob.rpg.client.module.graphic.service.facade;

import dev.peytob.rpg.client.module.graphic.model.Sprite;
import dev.peytob.rpg.client.module.graphic.model.TextureAtlas;
import dev.peytob.rpg.client.module.graphic.resource.Mesh;
import dev.peytob.rpg.client.module.graphic.resource.VertexArray;
import dev.peytob.rpg.client.module.graphic.service.vendor.MeshService;
import dev.peytob.rpg.core.module.location.model.tilemap.Tilemap;
import dev.peytob.rpg.core.module.location.resource.Tile;
import dev.peytob.rpg.math.vector.Vec2;
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

    private static final int BYTES_PER_VERTEX = 4 * Float.BYTES;

    /**
     * Target shader layout:
     * layout (location = 0) in vec2 a_tilePosition;
     * layout (location = 1) in vec2 a_normalizedTextureCoordinates;
     */
    private static final Collection<VertexArray.VertexArrayAttribute> SPRITE_BUFFER_ATTRIBUTES = List.of(
        new VertexArray.VertexArrayAttribute(0, 2, GL_FLOAT, false, BYTES_PER_VERTEX, 0L),
        new VertexArray.VertexArrayAttribute(1, 2, GL_FLOAT, false, BYTES_PER_VERTEX, 2L * Float.BYTES));

    private final MeshService meshService;

    public TilemapMeshServiceImpl(MeshService meshService) {
        this.meshService = meshService;
    }

    @Override
    public Mesh buildTilemapMesh(String textId, Tilemap tilemap, TextureAtlas textureAtlas) {

        // One rectangle -> Two triangles -> Six vertexes.
        int maximalVerticesCount = tilemap.getSizes().x() * tilemap.getSizes().y() * 6;
        int bufferCapacity = maximalVerticesCount * BYTES_PER_VERTEX;
        TilemapMeshBuilder tilemapMeshBuilder = new TilemapMeshBuilder(bufferCapacity, TILE_SIZE, textureAtlas);

        for (int x = 0; x < tilemap.getSizes().x(); x++) {
            for (int y = 0; y < tilemap.getSizes().y(); y++) {
                Tile tile = tilemap.getTile(x, y);

                if (tile != null) {
                    tilemapMeshBuilder.appendTile(x * TILE_SIZE.x(), y * TILE_SIZE.y(), tile);
                }
            }
        }

        ByteBuffer vboData = tilemapMeshBuilder.build();

        return meshService.createMesh(textId, vboData, tilemapMeshBuilder.getTotalVertices(), SPRITE_BUFFER_ATTRIBUTES);
    }

    private static final class TilemapMeshBuilder {

        private final ByteBuffer buffer;

        private final Vec2i tileSize;

        private final TextureAtlas textureAtlas;

        private int totalVertices;


        public TilemapMeshBuilder(int bufferCapacity, Vec2i tileSize, TextureAtlas textureAtlas) {
            this.buffer = BufferUtils.createByteBuffer(bufferCapacity);
            this.tileSize = tileSize;
            this.textureAtlas = textureAtlas;
            this.totalVertices = 0;
        }

        public void appendTile(float positionX, float positionY, Tile tile) {
            Sprite sprite = textureAtlas.getSpriteByEntityId(tile.textId());

            if (sprite == null) {
                // TODO Create default not-found texture
                return;
            }

            Vec2 texturePosition = sprite.normalizedTextureRect().topLeft();
            Vec2 textureSize = sprite.normalizedTextureRect().size();

            appendVertex(positionX, positionY, texturePosition.x(), texturePosition.y());
            appendVertex(positionX + tileSize.x(), positionY, texturePosition.x() + textureSize.x(), texturePosition.y());
            appendVertex(positionX, positionY + tileSize.y(), texturePosition.x(), texturePosition.y() + textureSize.y());

            appendVertex(positionX + tileSize.x(), positionY, texturePosition.x() + textureSize.x(), texturePosition.y());
            appendVertex(positionX, positionY + tileSize.y(), texturePosition.x(), texturePosition.y() + textureSize.y());
            appendVertex(positionX + tileSize.y(), positionY + tileSize.y(), texturePosition.x() + textureSize.x(), texturePosition.y() + textureSize.y());
        }

        public ByteBuffer build() {
            return buffer.flip();
        }

        public int getTotalVertices() {
            return totalVertices;
        }

        private void appendVertex(float positionX, float positionY, float textureX, float textureY) {
            this.totalVertices += 1;
            buffer
                .putFloat(positionX).putFloat(positionY)
                .putFloat(textureX).putFloat(textureY);
        }
    }
}
