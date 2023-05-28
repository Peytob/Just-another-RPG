package dev.peytob.rpg.client.module.graphic.pipeline;

import dev.peytob.rpg.client.module.graphic.repository.ShaderProgramRepository;
import dev.peytob.rpg.client.module.graphic.repository.UniformBlockRepository;
import dev.peytob.rpg.client.module.graphic.resource.ShaderProgram;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static org.lwjgl.opengl.GL31C.*;

@Component
public class UniformBlockBindingStep implements InitializingPipelineStep {

    private static final Logger logger = LoggerFactory.getLogger(UniformBlockBindingStep.class);

    private final ShaderProgramRepository shaderProgramRepository;

    private final UniformBlockRepository uniformBlockRepository;

    public UniformBlockBindingStep(ShaderProgramRepository shaderProgramRepository,
                                   UniformBlockRepository uniformBlockRepository) {
        this.shaderProgramRepository = shaderProgramRepository;
        this.uniformBlockRepository = uniformBlockRepository;
    }

    @Override
    public void execute() {
        shaderProgramRepository.getAll().forEach(this::bindUniformBlock);
    }

    private void bindUniformBlock(ShaderProgram shaderProgram) {
        uniformBlockRepository.getAll().forEach(uniformBlock -> {
            int bufferIndex = glGetUniformBlockIndex(shaderProgram.id(), uniformBlock.name());

            if (GL_INVALID_INDEX == bufferIndex) {
                return;
            }

            logger.info("Binding uniform block '{}' (buffer id: {}) with shader program {} (id: {}). Binding index {}",
                uniformBlock.name(), uniformBlock.buffer().id(), shaderProgram.textId(), shaderProgram.id(), bufferIndex);

            glUniformBlockBinding(shaderProgram.id(), bufferIndex, uniformBlock.bindingIndex());
        });
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
