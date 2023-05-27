package dev.peytob.rpg.math.vector;

public enum Vectors {;

    private static final Vec2 VEC2_ZERO = immutableVec2(0.0f, 0.0f);

    private static final Vec2i VEC2I_ZERO = immutableVec2i(0, 0);

    public static Vec2 immutableVec2() {
        return VEC2_ZERO;
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

    public static Vec2i immutableVec2i() {
        return VEC2I_ZERO;
    }

    public static Vec2i immutableVec2i(int x, int y) {
        return new ImmutableVec2i(x, y);
    }

    public static Vec2i immutableVec2i(Vec2i from) {
        if (from instanceof ImmutableVec2i) {
            return from;
        }

        return new ImmutableVec2i(from.x(), from.y());
    }
}
