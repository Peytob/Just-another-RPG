package dev.peytob.rpg.math.vector;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = ImmutableVec2.class)
public interface Vec2 {

    float x();

    float y();

    default Vec2 plus(Vec2 right) {
        return plus(right.x(), right.y());
    }

    default Vec2 plus(Vec2i right) {
        return plus(right.x(), right.y());
    }

    Vec2 plus(float x, float y);

    default Vec2 minus(Vec2 right) {
        return minus(right.x(), right.y());
    }

    default Vec2 minus(Vec2i right) {
        return minus(right.x(), right.y());
    }

    Vec2 minus(float x, float y);

    default Vec2 division(Vec2i right) {
        return division(right.x(), right.y());
    }

    default Vec2 division(Vec2 right) {
        return division(right.x(), right.y());
    }

    /**
     * Component-wise division
     * @return (l.x / r.x, l.y / l.x)
     */
    Vec2 division(float x, float y);

    Vec2 multiply(float scalar);
}
