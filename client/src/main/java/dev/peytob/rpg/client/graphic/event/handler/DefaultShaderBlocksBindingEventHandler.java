package dev.peytob.rpg.client.graphic.event.handler;

import dev.peytob.rpg.client.fsm.event.instance.AfterEngineStatePushEvent;
import dev.peytob.rpg.client.graphic.repository.ShaderProgramRepository;
import dev.peytob.rpg.client.graphic.repository.UniformBlockRepository;
import dev.peytob.rpg.client.graphic.resource.ShaderProgram;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static org.lwjgl.opengl.GL33.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultShaderBlocksBindingEventHandler {

    private final UniformBlockRepository uniformBlockRepository;

    private final ShaderProgramRepository shaderProgramRepository;

    @EventListener
    public void execute(AfterEngineStatePushEvent ignored) {
        shaderProgramRepository.getAll().forEach(this::bindUniformBlocks);
    }

    private void bindUniformBlocks(ShaderProgram shaderProgram) {
        uniformBlockRepository.getAll().forEach(uniformBlock -> {
            int bufferIndex = glGetUniformBlockIndex(shaderProgram.vendorId(), uniformBlock.id());

            if (GL_INVALID_INDEX == bufferIndex) {
                return;
            }

            log.info("Binding uniform block '{}' (buffer id: {}) with shader program {} (id: {}). Binding index {}",
                uniformBlock.id(), uniformBlock.buffer().id(), shaderProgram.id(), shaderProgram.vendorId(), bufferIndex);

            glUniformBlockBinding(shaderProgram.vendorId(), bufferIndex, uniformBlock.bindingIndex());
        });
    }
}
