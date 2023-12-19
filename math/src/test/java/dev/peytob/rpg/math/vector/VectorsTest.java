package dev.peytob.rpg.math.vector;

import org.junit.jupiter.api.Test;

import static dev.peytob.rpg.math.vector.Vectors.immutableVec2;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VectorsTest {

    @Test
    void normalizedVectorComputesCorrect() {
        Vec2 vec2 = immutableVec2(3, 4);
        Vec2 normalized = Vectors.normalize(vec2);
        assertEquals(immutableVec2(3/5f, 4/5f), normalized);
    }
}