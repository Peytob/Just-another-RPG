package dev.peytob.rpg.math.vector;

public interface Vec2 {

    float x();

    float y();

    default Vec2 plus(Vec2 right) {
        return plus(right.x(), right.y());
    }

    Vec2 plus(float x, float y);

    default Vec2 minus(Vec2 right) {
        return minus(right.x(), right.y());
    }

    Vec2 minus(float x, float y);
}