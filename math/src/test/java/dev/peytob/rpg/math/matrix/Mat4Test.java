package dev.peytob.rpg.math.matrix;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract class Mat4Test {

    abstract Mat4 create(float a11, float a12, float a13, float a14,
                         float a21, float a22, float a23, float a24,
                         float a31, float a32, float a33, float a34,
                         float a41, float a42, float a43, float a44);

    @Test
    void equalsMethodTest() {
        Mat4 first = create(
            1, 2, 3, 4,
            5, 6, 7, 8,
            9, 0, 1, 2,
            3, 4, 5, 6
        );

        Mat4 second = create(
            1, 2, 3, 4,
            5, 6, 7, 8,
            9, 0, 1, 2,
            3, 4, 5, 6
        );

        assertEquals(first, second);
    }
}