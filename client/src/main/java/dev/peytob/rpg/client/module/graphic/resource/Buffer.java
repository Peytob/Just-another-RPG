package dev.peytob.rpg.client.module.graphic.resource;

import dev.peytob.rpg.engine.resource.Resource;

public record Buffer(
        Integer id,
        String textId,
        Integer target
) implements Resource {
}
