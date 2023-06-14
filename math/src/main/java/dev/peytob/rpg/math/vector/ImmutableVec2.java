package dev.peytob.rpg.math.vector;

import java.util.Objects;

record ImmutableVec2(
    float x,
    float y
) implements Vec2 {

    @Override
    public Vec2 plus(float x, float y) {
        return new ImmutableVec2(this.x + x, this.y + y);
    }

    @Override
    public Vec2 minus(float x, float y) {
        return new ImmutableVec2(this.x - x, this.y - y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImmutableVec2 that = (ImmutableVec2) o;
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

    @Override
    public Vec2 division(float x, float y) {
        return new ImmutableVec2(this.x / x, this.y / y);
    }
}
