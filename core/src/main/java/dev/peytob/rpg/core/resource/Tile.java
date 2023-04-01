package dev.peytob.rpg.core.resource;

import dev.peytob.rpg.engine.resource.Resource;

public record Tile(
        Integer id,
        String textId
) implements Resource {
}
