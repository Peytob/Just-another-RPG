package dev.peytob.rpg.client.graphic.resource;

import dev.peytob.rpg.engine.resource.Resource;

public record Mesh(
    String id,
    GraphicBuffer vertexBufferObject,
    VertexArray vertexArray,
    Integer verticesCount
) implements Resource {
}
