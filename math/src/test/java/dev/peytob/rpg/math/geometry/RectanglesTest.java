package dev.peytob.rpg.math.geometry;

import org.junit.jupiter.api.Test;

import static dev.peytob.rpg.math.geometry.Rectangles.rect;
import static dev.peytob.rpg.math.geometry.Rectangles.rectI;
import static dev.peytob.rpg.math.vector.Vectors.immutableVec2;
import static dev.peytob.rpg.math.vector.Vectors.immutableVec2i;
import static org.junit.jupiter.api.Assertions.*;

class RectanglesTest {

    @Test
    void isRectCornersComputesCorrect() {
        Rect rect = rect(immutableVec2(5, 7), immutableVec2(13, 11));

        assertEquals(rect.topLeft(), immutableVec2(5, 7));
        assertEquals(rect.topRight(), immutableVec2(18, 7));
        assertEquals(rect.bottomLeft(), immutableVec2(5, 18));
        assertEquals(rect.bottomRight(), immutableVec2(18, 18));
    }

    @Test
    void isRectICornersComputesCorrect() {
        RectI rect = rectI(immutableVec2i(5, 7), immutableVec2i(13, 11));

        assertEquals(rect.topLeft(), immutableVec2i(5, 7));
        assertEquals(rect.topRight(), immutableVec2i(18, 7));
        assertEquals(rect.bottomLeft(), immutableVec2i(5, 18));
        assertEquals(rect.bottomRight(), immutableVec2i(18, 18));
    }
}