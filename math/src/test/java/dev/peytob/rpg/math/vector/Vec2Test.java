package dev.peytob.rpg.math.vector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract class Vec2Test {

    abstract Vec2 create(float x, float y);

    @Test
    void equalsMethodTest() {
        float x = 431.543f;
        float y = -223.3425f;

        Vec2 first = create(x, y);
        Vec2 second = create(x, y);

        assertEquals(first, second);
    }

    @Test
    void additionOperationTest() {
        Vec2 left = create(1.0f, 6.2f);
        Vec2 right = create(2.3f, 1.1f);

        Vec2 result = left.plus(right);

        assertEquals(result.x(), left.x() + right.x());
        assertEquals(result.y(), left.y() + right.y());
    }

    @Test
    void subtractionOperationTest() {
        Vec2 left = create(-4.2f, -10.0f);
        Vec2 right = create(3.1f, 5.3f);

        Vec2 result = left.minus(right);

        assertEquals(result.x(), left.x() - right.x());
        assertEquals(result.y(), left.y() - right.y());
    }
}