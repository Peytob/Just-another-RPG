package dev.peytob.rpg.math.matrix;

public interface Mat4 {

    int BYTES = Float.BYTES * 4 * 4;

    float get(int row, int column);

    void set(int row, int column, int data);

    float[] getRaw();
}
