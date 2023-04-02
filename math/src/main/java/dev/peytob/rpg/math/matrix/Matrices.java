package dev.peytob.rpg.math.matrix;

public enum Matrices {;

    public static Mat4 mutableMat4() {
        return new MutableMat4();
    }

    public static Mat4 mutableMat4(float a11, float a12, float a13, float a14,
                                   float a21, float a22, float a23, float a24,
                                   float a31, float a32, float a33, float a34,
                                   float a41, float a42, float a43, float a44) {
        return new MutableMat4(
                a11, a12, a13, a14,
                a21, a22, a23, a24,
                a31, a32, a33, a34,
                a41, a42, a43, a44
        );
    }

    public static Mat4 immutableMat4(Mat4 mat4) {
        if (mat4 instanceof ImmutableMat4 immutableMat4) {
            return immutableMat4;
        }

        return new ImmutableMat4(mat4);
    }

    /**
     * Creates orthographic matrix. See <a href="https://registry.khronos.org/OpenGL-Refpages/gl2.1/xhtml/glOrtho.xml">glOrtho method</a> for details.
     * Computed matrix will be **mutable**!
     */
    public static Mat4 ortho(float left, float right, float bottom, float top, float nearVal, float farVal) {
        float tx = -((right + left) / (right - left));
        float ty = -((top + bottom) / (top - bottom));
        float tz = -((farVal + nearVal) / (farVal - nearVal));

        return mutableMat4(
                2 / (right - left), 0, 0, 0,
                0, 2 / (top - bottom), 0, 0,
                0, 0, -2 / (farVal - nearVal), 0,
                tx, ty, tz, 1);
    }

    /**
     * Creates 2D orthographic matrix. See <a href="https://registry.khronos.org/OpenGL-Refpages/gl2.1/xhtml/gluOrtho2D.xml">glOrtho2D method</a> for details.
     * Computed matrix will be **mutable**!
     */
    public static Mat4 ortho2D(float left, float right, float bottom, float top) {
        return ortho(left, right, bottom, top, -1.0f, 1.0f);
    }
}
