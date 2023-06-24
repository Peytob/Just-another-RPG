package dev.peytob.rpg.client.module.graphic.pipeline;

import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import org.springframework.stereotype.Component;

import static org.lwjgl.opengl.GL11C.*;

@Component
public final class SetUpOpenGlPipelineStep implements InitializingPipelineStep {

    @Override
    public void execute() {
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_BLEND);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
