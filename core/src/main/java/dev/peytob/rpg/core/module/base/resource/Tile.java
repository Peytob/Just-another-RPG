package dev.peytob.rpg.core.module.base.resource;

import dev.peytob.rpg.engine.resource.Resource;

public record Tile(
        Integer id,
        String textId
) implements Resource {
}
