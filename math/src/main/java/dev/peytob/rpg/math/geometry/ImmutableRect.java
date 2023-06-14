package dev.peytob.rpg.math.geometry;

import dev.peytob.rpg.math.vector.Vec2;

record ImmutableRect(
    Vec2 topLeft,
    Vec2 topRight,
    Vec2 bottomRight,
    Vec2 bottomLeft,
    Vec2 size
) implements Rect {

    public ImmutableRect(Vec2 topLeft, Vec2 size) {
        this(topLeft, topLeft.plus(size.x(), 0), topLeft.plus(size.x(), size.y()), topLeft.plus(0, size.y()), size);
    }
}
