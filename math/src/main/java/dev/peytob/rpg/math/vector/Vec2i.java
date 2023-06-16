package dev.peytob.rpg.math.vector;

public interface Vec2i {

    int x();

    int y();

    default Vec2i plus(Vec2i right) {
        return plus(right.x(), right.y());
    }

    default Vec2i plus(Vec2 right) {
        return plus((int) right.x(), (int) right.y());
    }

    Vec2i plus(int x, int y);

    default Vec2i minus(Vec2i right) {
        return minus(right.x(), right.y());
    }

    default Vec2i minus(Vec2 right) {
        return minus((int) right.x(), (int) right.y());
    }

    Vec2i minus(int x, int y);

    default Vec2i division(Vec2i right) {
        return division(right.x(), right.y());
    }

    default Vec2i division(Vec2 right) {
        return division((int) right.x(), (int) right.y());
    }

    /**
     * Component-wise division
     * @return (l.x / r.x, l.y / l.x)
     */
    Vec2i division(int x, int y);
}
