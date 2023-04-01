package dev.peytob.rpg.math.vector;

import java.util.Objects;

record ImmutableVec2i(
        int x,
        int y
) implements Vec2i {

    @Override
    public Vec2i plus(int x, int y) {
        return new ImmutableVec2i(this.x + x, this.y + y);
    }

    @Override
    public Vec2i minus(int x, int y) {
        return new ImmutableVec2i(this.x - x, this.y - y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImmutableVec2i that = (ImmutableVec2i) o;
        return Float.compare(that.x, x) == 0 && Float.compare(that.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x() + ", " + y() + ")";
    }
}
