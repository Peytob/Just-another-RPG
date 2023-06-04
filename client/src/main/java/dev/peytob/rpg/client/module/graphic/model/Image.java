package dev.peytob.rpg.client.module.graphic.model;

import dev.peytob.rpg.math.vector.Vec2i;

import java.nio.ByteBuffer;

public record Image(
    Vec2i sizes,
    ByteBuffer data
) {
}
