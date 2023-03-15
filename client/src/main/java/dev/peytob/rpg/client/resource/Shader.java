package dev.peytob.rpg.client.resource;

import dev.peytob.rpg.engine.resource.Resource;

public record Shader(
        Integer id,
        String textId,
        Integer type
) implements Resource {
}
