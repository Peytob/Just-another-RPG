package dev.peytob.rpg.client.graphic.resource;

import dev.peytob.rpg.engine.resource.Resource;

public record UniformBlock(
    String id,
    Integer sizesBytes,
    Integer bindingIndex,
    GraphicBuffer buffer
) implements Resource {
}
