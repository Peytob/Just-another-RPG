package dev.peytob.rpg.client.module.graphic.service.facade;

import dev.peytob.rpg.client.module.graphic.model.RenderableTilemap;
import dev.peytob.rpg.client.module.graphic.model.Sprite;
import dev.peytob.rpg.client.module.graphic.model.TextureAtlas;
import dev.peytob.rpg.client.module.graphic.resource.Mesh;
import dev.peytob.rpg.client.module.graphic.resource.VertexArray;
import dev.peytob.rpg.client.module.graphic.service.vendor.MeshService;
import dev.peytob.rpg.core.module.location.model.tilemap.Tilemap;
import dev.peytob.rpg.core.module.location.resource.Tile;
import dev.peytob.rpg.math.geometry.RectI;
import dev.peytob.rpg.math.vector.Vec2;
import dev.peytob.rpg.math.vector.Vec2i;
import org.lwjgl.BufferUtils;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;

import static dev.peytob.rpg.math.vector.Vectors.immutableVec2;
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
    public Mesh buildTilemapMesh(String textId, RenderableTilemap renderableTilemap) {

        RectI cullingTilesRect = renderableTilemap.getCullingTilesRect();
        Tilemap tilemap = renderableTilemap.getTilemap();
        Vec2i tileSize = renderableTilemap.getTileSize();

        TilemapMeshBuilder tilemapMeshBuilder = new TilemapMeshBuilder(
            tilemap.getSizes().x() * tilemap.getSizes().y(),
            tileSize,
            renderableTilemap.getTextureAtlas());

//        int fromX = max(cullingTilesRect.topLeft().x(), 0);
//        int toX = min(cullingTilesRect.bottomRight().x(), tilemap.getSizes().x());
//        int fromY = max(cullingTilesRect.topLeft().y(), 0);
//        int toY = min(cullingTilesRect.bottomRight().y(), tilemap.getSizes().y());

        int fromX = 0;
        int toX = tilemap.getSizes().x();
        int fromY = 0;
        int toY = tilemap.getSizes().y();

        for (int x = fromX; x < toX; x++) {
            for (int y = fromY; y < toY; y++) {
                Tile tile = tilemap.getTile(x, y);

                if (tile != null) {
                    tilemapMeshBuilder.appendTile(x, y, tile);
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

        public TilemapMeshBuilder(int spritesCapacity, Vec2i tileSize, TextureAtlas textureAtlas) {
            // One rectangle -> Two triangles -> Six vertexes.
            int bufferCapacity = spritesCapacity * 6 * BYTES_PER_VERTEX;
            this.buffer = BufferUtils.createByteBuffer(bufferCapacity);
            this.tileSize = tileSize;
            this.textureAtlas = textureAtlas;
            this.totalVertices = 0;
        }

        public void appendTile(float gridX, float gridY, Tile tile) {
            Sprite sprite = textureAtlas.getSpriteByEntityId(tile.textId());

            if (sprite == null) {
                // TODO Create default not-found texture
                return;
            }

            Vec2 texturePosition = sprite.normalizedTextureRect().topLeft();
            Vec2 textureSize = sprite.normalizedTextureRect().size();

            Vec2 position = immutableVec2(gridX, gridY);
            Vec2 xT = immutableVec2(0.5f * tileSize.x(), 0.25f * tileSize.y()).multiply(position.x());
            Vec2 yT = immutableVec2(-0.5f * tileSize.x(), 0.25f * tileSize.y()).multiply(position.y());
            Vec2 pT = yT.plus(xT);

            float positionX = pT.x();
            float positionY = pT.y();

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
