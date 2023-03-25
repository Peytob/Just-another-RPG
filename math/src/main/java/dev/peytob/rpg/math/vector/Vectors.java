package dev.peytob.rpg.math.vector;

public enum Vectors {;

    public static Vec2 immutableVec2() {
        return immutableVec2(0, 0);
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
        return immutableVec2i(0, 0);
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
