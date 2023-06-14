package dev.peytob.rpg.math.geometry;

import dev.peytob.rpg.math.vector.Vec2;

public interface Rect {
    Vec2 topLeft();
    Vec2 topRight();
    Vec2 bottomRight();
    Vec2 bottomLeft();
    Vec2 size();
}
