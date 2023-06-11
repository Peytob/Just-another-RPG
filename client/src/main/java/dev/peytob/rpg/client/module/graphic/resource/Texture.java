package dev.peytob.rpg.client.module.graphic.resource;

import dev.peytob.rpg.engine.resource.Resource;
import dev.peytob.rpg.math.vector.Vec2i;

import java.nio.ByteBuffer;

public record Texture(
    Integer id,
    String textId,
    Integer format,
    Vec2i sizes,
    ByteBuffer data
) implements Resource {
}
