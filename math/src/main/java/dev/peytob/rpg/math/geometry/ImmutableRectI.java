package dev.peytob.rpg.math.geometry;

import dev.peytob.rpg.math.vector.Vec2i;

public record ImmutableRectI(
    Vec2i topLeft,
    Vec2i size
) implements RectI {
}
