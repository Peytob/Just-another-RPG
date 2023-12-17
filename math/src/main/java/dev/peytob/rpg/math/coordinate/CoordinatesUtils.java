package dev.peytob.rpg.math.coordinate;

import dev.peytob.rpg.math.vector.Vec2i;

public enum CoordinatesUtils {;

    /**
     * Returns true if rect contains this coordinates. Upper left corner of the rect in 0, 0 coordinate.
     */
    public static boolean isCoordinatesInRectI(int x, int y, int rectSizeX, int rectSizeY) {
        return 0 <= x && x < rectSizeX && 0 <= y && y < rectSizeY;
    }

    public static boolean isCoordinatesInRectI(Vec2i coordinates, Vec2i rectSize) {
        return isCoordinatesInRectI(coordinates.x(), coordinates.y(), rectSize.x(), rectSize.y());
    }

    public static boolean isCoordinatesInRectI(int x, int y, Vec2i rectSize) {
        return isCoordinatesInRectI(x, y, rectSize.x(), rectSize.y());
    }
}
