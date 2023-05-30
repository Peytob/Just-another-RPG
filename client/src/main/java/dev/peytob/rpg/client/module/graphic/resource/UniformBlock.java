package dev.peytob.rpg.client.module.graphic.resource;

import dev.peytob.rpg.engine.resource.Resource;

public record UniformBlock(
    Integer id,
    String textId,
    Integer sizesInBytes,
    Integer bindingIndex,
    String name,
    Buffer buffer
) implements Resource {
}
