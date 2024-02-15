package dev.peytob.rpg.client.loading.event.handler;

import dev.peytob.rpg.client.fsm.event.instance.BeforeInitializingPipelineExecuteEvent;
import dev.peytob.rpg.client.graphic.model.DefaultUniformBlocks;
import dev.peytob.rpg.client.graphic.model.opengl.ShaderType;
import dev.peytob.rpg.client.graphic.repository.UniformBlockRepository;
import dev.peytob.rpg.client.graphic.resource.Shader;
import dev.peytob.rpg.client.graphic.resource.ShaderProgram;
import dev.peytob.rpg.client.graphic.service.ShaderService;
import dev.peytob.rpg.client.graphic.service.UniformBlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;

import static dev.peytob.rpg.client.graphic.model.DefaultShaderPrograms.TILEMAP_SHADER_PROGRAM_ID;
import static org.lwjgl.opengl.GL31.*;

/**
 * Cant be called from InitializingPipeline, because initializing pipeline is called async.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class LoadDefaultShaderProgramsInitializingStep {

    private final ShaderService shaderService;

    private final UniformBlockRepository uniformBlockRepository;

    private final UniformBlockService uniformBlockService;

    private final ResourceLoader resourceLoader;

    @EventListener
    public void execute(BeforeInitializingPipelineExecuteEvent ignored) {
        createDefaultUniformsBlocks();

        ShaderProgram tilemapShaderProgram = loadShaderProgram(TILEMAP_SHADER_PROGRAM_ID, "/shaders/tilemap.vert", "/shaders/tilemap.frag");
        bindUniformBlocks(tilemapShaderProgram);
    }

    private ShaderProgram loadShaderProgram(String id, String vertexShaderLocation, String fragmentShaderLocation) {
        Shader vertexShader = loadShader(id, vertexShaderLocation, ShaderType.VERTEX_SHADER);
        Shader fragmentShader = loadShader(id, fragmentShaderLocation, ShaderType.FRAGMENT_SHADER);
        ShaderProgram shaderProgram = shaderService.createShaderProgram(id, vertexShader, fragmentShader, null);
        shaderService.removeShader(vertexShader);
        shaderService.removeShader(fragmentShader);
        return shaderProgram;
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

    private Shader loadShader(String shaderProgramId, String location, ShaderType shaderType) {
        String shaderSource = loadFile(location);
        String shaderId = shaderProgramId + "_" + shaderType.name();
        return shaderService.compileShader(shaderId, shaderSource, shaderType);
    }

    private void createDefaultUniformsBlocks() {
        for (var defaultUniformBlockData : DefaultUniformBlocks.values()) {
            uniformBlockService.createUniformBlock(
                    defaultUniformBlockData.getName(),
                    defaultUniformBlockData.getSizes(),
                    defaultUniformBlockData.getBindingIndex());
        }
    }

    private String loadFile(String location) {
        try {
            return resourceLoader
                .getResource(location)
                .getContentAsString(Charset.defaultCharset());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
