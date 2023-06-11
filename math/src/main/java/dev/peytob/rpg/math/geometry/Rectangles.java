package dev.peytob.rpg.math.geometry;

import dev.peytob.rpg.math.vector.Vec2;
import dev.peytob.rpg.math.vector.Vec2i;

import static dev.peytob.rpg.math.vector.Vectors.immutableVec2;
import static dev.peytob.rpg.math.vector.Vectors.immutableVec2i;

public enum Rectangles {;

    public static RectI rectI(Vec2i topLeft, Vec2i sizes) {
        return new ImmutableRectI(topLeft, sizes);
    }

    public static RectI rectI(int topLeftX, int topLeftY, int width, int height) {
        return rectI(immutableVec2i(topLeftX, topLeftY), immutableVec2i(width, height));
    }

    public static Rect rect(Vec2 topLeft, Vec2 sizes) {
        return new ImmutableRect(topLeft, sizes);
    }

    public static Rect rect(float topLeftX, float topLeftY, float width, float height) {
        return rect(immutableVec2(topLeftX, topLeftY), immutableVec2(width, height));
    }
}
