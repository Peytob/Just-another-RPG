package dev.peytob.rpg.client.graphic.model.opengl;

import static org.lwjgl.opengl.GL33.*;

public enum BufferTarget {

    ARRAY_BUFFER(GL_ARRAY_BUFFER),

    UNIFORM_BUFFER(GL_UNIFORM_BUFFER),

    ELEMENT_ARRAY_BUFFER(GL_ELEMENT_ARRAY_BUFFER);

    private final int glTarget;

    BufferTarget(int glTarget) {
        this.glTarget = glTarget;
    }

    public int getGlTarget() {
        return glTarget;
    }
}
