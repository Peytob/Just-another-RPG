package dev.peytob.rpg.client.module.graphic.model;

import static org.lwjgl.opengl.GL11C.GL_FILL;

public final class RenderContext {

    private Integer shaderProgramId;

    private Integer renderMode;

    private Integer polygonMode = GL_FILL;

    private Integer textureId;

    public Integer getShaderProgramId() {
        return shaderProgramId;
    }

    public void setShaderProgramId(Integer shaderProgramId) {
        this.shaderProgramId = shaderProgramId;
    }

    public Integer getRenderMode() {
        return renderMode;
    }

    public void setRenderMode(Integer renderMode) {
        this.renderMode = renderMode;
    }

    public Integer getPolygonMode() {
        return polygonMode;
    }

    public void setPolygonMode(Integer polygonMode) {
        this.polygonMode = polygonMode;
    }

    public void setTextureId(Integer textureId) {
        this.textureId = textureId;
    }

    public Integer getTextureId() {
        return this.textureId;
    }
}
