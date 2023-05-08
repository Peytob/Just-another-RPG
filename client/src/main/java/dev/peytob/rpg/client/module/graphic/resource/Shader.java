package dev.peytob.rpg.client.module.graphic.resource;

import dev.peytob.rpg.engine.resource.Resource;

public record Shader(
        Integer id,
        String textId,
        Integer type
) implements Resource {
}
