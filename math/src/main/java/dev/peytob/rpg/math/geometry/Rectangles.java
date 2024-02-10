package dev.peytob.rpg.math.geometry;

import dev.peytob.rpg.math.vector.Vec2;
import dev.peytob.rpg.math.vector.Vec2i;

import static dev.peytob.rpg.math.vector.Vectors.immutableVec2;
import static dev.peytob.rpg.math.vector.Vectors.immutableVec2i;

public enum Rectangles {;

    private static final Rect ZERO_RECT = new ImmutableRect(immutableVec2(), immutableVec2());
    private static final RectI ZERO_RECTI = new ImmutableRectI(immutableVec2i(), immutableVec2i());

    public static RectI rectI(Vec2i topLeft, Vec2i sizes) {
        return new ImmutableRectI(topLeft, sizes);
    }

    public static RectI rectI(int topLeftX, int topLeftY, int width, int height) {
        return rectI(immutableVec2i(topLeftX, topLeftY), immutableVec2i(width, height));
    }

    public static RectI rectI() {
        return ZERO_RECTI;
    }

    public static Rect rect(Vec2 topLeft, Vec2 sizes) {
        return new ImmutableRect(topLeft, sizes);
    }

    public static Rect rect(float topLeftX, float topLeftY, float width, float height) {
        return rect(immutableVec2(topLeftX, topLeftY), immutableVec2(width, height));
    }

    public static Rect rect() {
        return ZERO_RECT;
    }

    public static Rect normalizeInside(Rect rect, Vec2 outerSizes) {
        return rect(
            rect.topLeft().division(outerSizes),
            rect.size().division(outerSizes)
        );
    }

    public static Rect normalizeInside(RectI rect, Vec2i outerSizes) {
        return rect(
            (float) rect.topLeft().x() / outerSizes.x(),
            (float) rect.topLeft().y() / outerSizes.y(),
            (float) rect.size().x() / outerSizes.x(),
            (float) rect.size().y() / outerSizes.y()
        );
    }
}
