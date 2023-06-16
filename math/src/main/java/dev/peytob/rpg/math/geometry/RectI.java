package dev.peytob.rpg.math.geometry;

import dev.peytob.rpg.math.vector.Vec2i;

public interface RectI {
    Vec2i topLeft();
    Vec2i topRight();
    Vec2i bottomRight();
    Vec2i bottomLeft();
    Vec2i size();
}
