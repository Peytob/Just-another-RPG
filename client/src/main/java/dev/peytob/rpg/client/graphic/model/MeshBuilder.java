package dev.peytob.rpg.client.graphic.model;

import dev.peytob.rpg.client.graphic.resource.Sprite;
import dev.peytob.rpg.client.graphic.resource.Texture;
import dev.peytob.rpg.client.graphic.resource.VertexArray.VertexArrayAttribute;
import dev.peytob.rpg.math.geometry.Rect;
import dev.peytob.rpg.math.vector.Vec2;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dev.peytob.rpg.client.graphic.model.RenderingConstants.RENDERING_COEFFICIENT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;

public class MeshBuilder {

    private static final int STRIDE = 4 * Float.BYTES + Integer.BYTES;

    private static final Collection<VertexArrayAttribute> VERTEX_ARRAY_ATTRIBUTES = List.of(
        new VertexArrayAttribute(0, 2, GL_FLOAT, false, STRIDE, 0L), // Position
        new VertexArrayAttribute(1, 2, GL_FLOAT, false, STRIDE, 2L * Float.BYTES), // Texture coordinates
        new VertexArrayAttribute(2, 1, GL_UNSIGNED_INT, false, STRIDE, 4L * Float.BYTES) // Texture index
    );

    public static final int BYTES_PER_VERTEX = STRIDE;

    public static MeshBuilder withCapacityBytes(int capacityBytes) {
        return new MeshBuilder(capacityBytes);
    }

    public static MeshBuilder withCapacityVertexes(int vertexesCount) {
        return withCapacityBytes(vertexesCount * BYTES_PER_VERTEX);
    }

    public static MeshBuilder withCapacitySprites(int spritesCount) {
        return withCapacityVertexes(spritesCount * 6);
    }

    private final ByteBuffer byteBuffer;

    private int verticesCount;

    private final Map<Texture, Integer> textureIndexes;

    private int nextTextureIndex;

    private MeshBuilder(int bytesCapacity) {
        this(BufferUtils.createByteBuffer(bytesCapacity), 0, new HashMap<>());
    }

    private MeshBuilder(ByteBuffer byteBuffer, int verticesCount, Map<Texture, Integer> textureIndexes) {
        this.byteBuffer = byteBuffer;
        this.verticesCount = verticesCount;
        this.textureIndexes = textureIndexes;
        this.nextTextureIndex = 0;
    }

    // All coordinates should be normalized, not pixels
    public MeshBuilder addSprite(Sprite sprite, Rect spriteRect) {
        return addSprite(sprite, spriteRect.topLeft(), spriteRect.size());
    }

    // All coordinates should be normalized, not pixels
    public MeshBuilder addSprite(Sprite sprite, Vec2 position, Vec2 sizes) {
        int textureIndex = getTextureIndex(sprite.getTexture());
        Rect textureRect = sprite.getNormalizedTextureRect();

        // 1----2
        // |    | <- some sprite
        // 3----4

        // First triangle 1 - 2 - 3
        addVertex( // 1
            position.x(), position.y(),
            textureRect.topLeft().x(), textureRect.topLeft().y(),
            textureIndex
        );

        addVertex( // 2
            position.x() + sizes.x(), position.y(),
            textureRect.topRight().x(), textureRect.topRight().y(),
            textureIndex
        );

        addVertex( // 3
            position.x(), position.y() + sizes.y(),
            textureRect.bottomLeft().x(), textureRect.bottomLeft().y(),
            textureIndex
        );

        // Second triangle 2 - 3 - 4
        addVertex( // 2
            position.x() + sizes.x(), position.y(),
            textureRect.topRight().x(), textureRect.topRight().y(),
            textureIndex
        );

        addVertex( // 3
            position.x(), position.y() + sizes.y(),
            textureRect.bottomLeft().x(), textureRect.bottomLeft().y(),
            textureIndex
        );

        addVertex( // 4
            position.x() + sizes.x(), position.y() + sizes.y(),
            textureRect.bottomRight().x(), textureRect.bottomRight().y(),
            textureIndex
        );

        return this;
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

    public int getVerticesCount() {
        return verticesCount;
    }

    public Map<Texture, Integer> getTextureIndexes() {
        return textureIndexes;
    }

    public Collection<VertexArrayAttribute> getAttributes() {
        return VERTEX_ARRAY_ATTRIBUTES;
    }

    private int getTextureIndex(Texture texture) {
        if (!textureIndexes.containsKey(texture)) {
            textureIndexes.put(texture, nextTextureIndex);
            nextTextureIndex++;
            return nextTextureIndex;
        }

        return textureIndexes.get(texture);
    }

    private void addVertex(float positionX, float positionY, float textureX, float textureY, int textureIndex) {
        byteBuffer
            .putFloat(positionX * RENDERING_COEFFICIENT).putFloat(positionY * RENDERING_COEFFICIENT)
            .putFloat(textureX).putFloat(textureY)
            .putInt(textureIndex);

        verticesCount++;
    }
}
