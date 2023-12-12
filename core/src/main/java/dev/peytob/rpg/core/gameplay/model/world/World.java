package dev.peytob.rpg.core.gameplay.model.world;

import dev.peytob.rpg.core.gameplay.model.world.tilemap.Tilemap;
import dev.peytob.rpg.engine.resource.Resource;

public record World(
    String id,
    Tilemap tilemap
) implements Resource {
}
