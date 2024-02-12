package dev.peytob.rpg.client.graphic.model.opengl;

import static org.lwjgl.opengl.GL33.*;

public enum RenderMode {
    POINTS(GL_POINTS),
    LINES(GL_LINES),
    TRIANGLES(GL_TRIANGLES);

    private final int glValue;

    RenderMode(int glValue) {
        this.glValue = glValue;
    }

    public int getGlValue() {
        return glValue;
    }
}
