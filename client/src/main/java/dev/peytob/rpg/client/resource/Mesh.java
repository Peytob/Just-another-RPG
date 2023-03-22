package dev.peytob.rpg.client.resource;

import dev.peytob.rpg.engine.resource.Resource;

public record Mesh(
        Integer id,
        String textId,
        VertexArray vertexArray,
        Buffer vertexBufferObject,
        Integer verticesCount
) implements Resource {
}
