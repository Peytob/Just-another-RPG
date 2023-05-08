package dev.peytob.rpg.client.module.graphic.resource;

import dev.peytob.rpg.engine.resource.Resource;

public record VertexArray(
        Integer id,
        String textId
) implements Resource {

    public record VertexArrayAttribute(
            Integer index,
            Integer size,
            Integer type,
            Boolean normalized,
            Integer stride,
            Long offset
    ) {
    }
}
