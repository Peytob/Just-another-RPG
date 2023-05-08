package dev.peytob.rpg.client.module.graphic.model;

public final class RenderContext {

    private Integer shaderProgramId;

    private Integer renderMode;

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
}
