package dev.peytob.rpg.client.module.graphic.resource;

import dev.peytob.rpg.engine.resource.Resource;

public record UniformBuffer(
    Integer id,
    String textId,
    Integer sizesInBytes,
    Buffer buffer
) implements Resource {
}
