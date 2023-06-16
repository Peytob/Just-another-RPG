package dev.peytob.rpg.math.matrix;

import java.util.Arrays;

final class MutableMat4 implements Mat4 {

    private final float[] values;

    MutableMat4() {
        this.values = new float[16];
    }

    MutableMat4(float a11, float a12, float a13, float a14,
                float a21, float a22, float a23, float a24,
                float a31, float a32, float a33, float a34,
                float a41, float a42, float a43, float a44) {
        this.values = new float[] {
            a11, a12, a13, a14,
            a21, a22, a23, a24,
            a31, a32, a33, a34,
            a41, a42, a43, a44
        };
    }

    @Override
    public float get(int row, int column) {
        return values[toIndex(row, column)];
    }

    @Override
    public void set(int row, int column, int data) {
        values[toIndex(row, column)] = data;
    }

    @Override
    public float[] getRaw() {
        return values;
    }

    private int toIndex(int row, int column) {
        return row * 4 + column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if (o instanceof MutableMat4 that) {
            return Arrays.equals(values, that.values);
        } else if (o instanceof Mat4 that) {
            return Arrays.equals(values, that.getRaw());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }
}
