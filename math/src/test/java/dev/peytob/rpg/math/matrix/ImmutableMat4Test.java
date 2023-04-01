package dev.peytob.rpg.math.matrix;

public class ImmutableMat4Test extends Mat4Test {

    @Override
    Mat4 create(float a11, float a12, float a13, float a14,
                float a21, float a22, float a23, float a24,
                float a31, float a32, float a33, float a34,
                float a41, float a42, float a43, float a44) {
        Mat4 matrix = Matrices.mutableMat4(a11, a12, a13, a14, a21, a22, a23, a24, a31, a32, a33, a34, a41, a42, a43, a44);
        return Matrices.immutableMat4(matrix);
    }
}
