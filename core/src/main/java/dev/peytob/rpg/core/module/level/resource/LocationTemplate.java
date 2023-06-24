package dev.peytob.rpg.core.module.level.resource;

import dev.peytob.rpg.core.module.level.model.tilemap.Tilemap;
import dev.peytob.rpg.engine.resource.Resource;

/**
 * Base immutable version of some location.
 * TODO Move to server module later!
 */
public record LocationTemplate(
        Integer id,
        String textId,
        Tilemap tilemap
) implements Resource {
}
