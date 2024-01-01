package dev.peytob.rpg.server.gameplay.resource;

import dev.peytob.rpg.engine.resource.Resource;

public record Character(
    String id,
    String userId,
    String name
) implements Resource {
}
