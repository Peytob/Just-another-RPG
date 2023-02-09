package dev.peytob.rpg.math.vector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ImmutableVec2Test extends Vec2Test {

    private static final Vec2 LEFT_VECTOR = Vectors.immutableVec2(1.0f, 3.0f);
    private static final Vec2 RIGHT_VECTOR = Vectors.immutableVec2(5.0f, -22.1f);

    @Override
    Vec2 create(float x, float y) {
        return Vectors.immutableVec2(x, y);
    }

    @Test
    void subtractionOperationReturnsNewVector() {
        Vec2 result = LEFT_VECTOR.minus(RIGHT_VECTOR);

        assertNotSame(LEFT_VECTOR, result);
        assertNotSame(RIGHT_VECTOR, result);
    }

    @Test
    void additionOperationReturnsNewVector() {
        Vec2 result = LEFT_VECTOR.plus(RIGHT_VECTOR);

        assertNotSame(LEFT_VECTOR, result);
        assertNotSame(RIGHT_VECTOR, result);
    }
}
