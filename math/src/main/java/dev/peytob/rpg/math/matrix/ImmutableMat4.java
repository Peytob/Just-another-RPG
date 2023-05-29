package dev.peytob.rpg.math.matrix;

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
}
