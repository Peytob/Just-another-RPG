package dev.peytob.rpg.core.resource;

import dev.peytob.rpg.core.model.location.tilemap.Tilemap;

/**
 * Base immutable version of some location
 */
public record LocationTemplate(
        Integer id,
        String textId,
        Tilemap tilemap
) {
}
