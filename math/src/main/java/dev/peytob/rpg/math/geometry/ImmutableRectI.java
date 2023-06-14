package dev.peytob.rpg.math.geometry;

import dev.peytob.rpg.math.vector.Vec2i;

public record ImmutableRectI(
    Vec2i topLeft,
    Vec2i topRight,
    Vec2i bottomRight,
    Vec2i bottomLeft,
    Vec2i size
) implements RectI {

    public ImmutableRectI(Vec2i topLeft, Vec2i size) {
        this(topLeft, topLeft.plus(size.x(), 0), topLeft.plus(size.x(), size.y()), topLeft.plus(0, size.y()), size);
    }
}
