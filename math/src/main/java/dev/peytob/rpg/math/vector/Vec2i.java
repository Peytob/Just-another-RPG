package dev.peytob.rpg.math.vector;

public interface Vec2i {

    int BYTES = Integer.BYTES * 2;

    int x();

    int y();

    default Vec2i plus(Vec2i right) {
        return plus(right.x(), right.y());
    }

    Vec2i plus(int x, int y);

    default Vec2i minus(Vec2i right) {
        return minus(right.x(), right.y());
    }

    Vec2i minus(int x, int y);
}
