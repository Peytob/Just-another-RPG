package dev.peytob.rpg.math.geometry;

import dev.peytob.rpg.math.vector.Vec2;

record ImmutableRect(
    Vec2 topLeft,
    Vec2 size
) implements Rect {
}
