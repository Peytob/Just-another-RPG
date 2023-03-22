package dev.peytob.rpg.client.resource;

import dev.peytob.rpg.engine.resource.Resource;

public record Buffer(
        Integer id,
        String textId,
        Integer target
) implements Resource {
}
