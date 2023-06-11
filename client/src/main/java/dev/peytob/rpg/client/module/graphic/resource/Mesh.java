package dev.peytob.rpg.client.module.graphic.resource;

import dev.peytob.rpg.engine.resource.Resource;

public record Mesh(
        Integer id,
        String textId,
        VertexArray vertexArray,
        Buffer vertexBuffer,
        Integer verticesCount
) implements Resource {
}
