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
}
