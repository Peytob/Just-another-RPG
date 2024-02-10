package dev.peytob.rpg.client.graphic.model.opengl;

import static org.lwjgl.opengl.GL33.*;

public enum ShaderType {
    VERTEX_SHADER(GL_VERTEX_SHADER),
    GEOMETRY_SHADER(GL_GEOMETRY_SHADER),
    FRAGMENT_SHADER(GL_FRAGMENT_SHADER);

    private final int glType;

    ShaderType(int glType) {
        this.glType = glType;
    }

    public int getGlType() {
        return glType;
    }
}
