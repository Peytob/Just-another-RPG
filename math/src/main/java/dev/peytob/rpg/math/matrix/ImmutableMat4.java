package dev.peytob.rpg.math.matrix;

import java.util.Objects;

final class ImmutableMat4 implements Mat4 {

    private final Mat4 mat4;

    public ImmutableMat4(Mat4 mat4) {
        this.mat4 = mat4;
    }

    @Override
    public float get(int row, int column) {
        return mat4.get(row, column);
    }

    @Override
    public void set(int row, int column, int data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float[] getRaw() {
        return mat4.getRaw();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImmutableMat4 that = (ImmutableMat4) o;
        return mat4.equals(that.mat4);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mat4);
    }
}
