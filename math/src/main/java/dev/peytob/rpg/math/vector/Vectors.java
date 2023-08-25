package dev.peytob.rpg.math.vector;

import static java.lang.Math.sqrt;

public enum Vectors {;

    private static final Vec2 ZERO_VEC = new ImmutableVec2(0.0f, 0.0f);
    private static final Vec2i ZERO_VECI = new ImmutableVec2i(0, 0);

    public static Vec2 immutableVec2() {
        return ZERO_VEC;
    }

    public static Vec2 immutableVec2(float x, float y) {
        return new ImmutableVec2(x, y);
    }

    public static Vec2 immutableVec2(Vec2 from) {
        if (from instanceof ImmutableVec2) {
            return from;
        }

        return new ImmutableVec2(from.x(), from.y());
    }

    public static Vec2 immutableVec2(Vec2i from) {
        return immutableVec2(from.x(), from.y());
    }

    public static Vec2i immutableVec2i() {
        return ZERO_VECI;
    }

    public static Vec2i immutableVec2i(int x, int y) {
        return new ImmutableVec2i(x, y);
    }

    public static Vec2i immutableVec2i(Vec2 from) {
        return immutableVec2i((int) from.x(), (int) from.y());
    }

    public static Vec2i immutableVec2i(Vec2i from) {
        if (from instanceof ImmutableVec2i) {
            return from;
        }

        return new ImmutableVec2i(from.x(), from.y());
    }

    public static float length(Vec2 vec2) {
        return (float) sqrt(vec2.x() * vec2.x() + vec2.y() * vec2.y());
    }

    public static Vec2 normalize(Vec2 vec2) {
        if (vec2.equals(ZERO_VEC)) {
            return ZERO_VEC;
        }

        float length = length(vec2);
        return immutableVec2(vec2.x() / length, vec2.y() / length);
    }
}
