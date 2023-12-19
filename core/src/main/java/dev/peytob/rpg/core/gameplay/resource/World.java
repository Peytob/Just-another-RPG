package dev.peytob.rpg.core.gameplay.resource;

import dev.peytob.rpg.core.gameplay.resource.tilemap.Tilemap;
import dev.peytob.rpg.engine.resource.Resource;

public record World(
    String id,
    Tilemap tilemap
) implements Resource {
}
