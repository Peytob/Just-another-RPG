package dev.peytob.rpg.client.module.graphic.pipeline;

import dev.peytob.rpg.client.module.graphic.resource.Shader;
import dev.peytob.rpg.client.module.graphic.service.vendor.ShaderService;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static dev.peytob.rpg.client.module.graphic.model.DefaultShaderProgramsIds.TILEMAP_SHADER_PROGRAM_TEXT_ID;
import static dev.peytob.rpg.engine.utils.FileUtils.readClasspathFile;
import static org.lwjgl.opengl.GL32.*;

// TODO Its can be optimized - some shaders might be loaded and compiled many times

@Component
public final class LoadDefaultShaderProgramsStep implements InitializingPipelineStep {

    private static final Logger logger = LoggerFactory.getLogger(LoadDefaultShaderProgramsStep.class);

    private final ShaderService shaderService;

    public LoadDefaultShaderProgramsStep(ShaderService shaderService) {
        this.shaderService = shaderService;
    }

    @Override
    public void execute() {
        final String defaultFilter = "shaders";

        loadShaderProgram(
            TILEMAP_SHADER_PROGRAM_TEXT_ID,
            defaultFilter + "/tilemap.vert",
            defaultFilter + "/tilemap.frag",
            defaultFilter + "/tilemap.geom");
    }

    private void loadShaderProgram(String programTextId, String vertexShaderFile, String fragmentShaderFile, String geometryShaderFile) {
        logger.info("Loading default shader program with id {}", programTextId);
        ClassLoader classLoader = getClass().getClassLoader();

        try {
            String vertexShaderCode = readClasspathFile(classLoader, vertexShaderFile);
            Shader vertexShader = shaderService.compileShader(vertexShaderCode, programTextId + "_vertex", GL_VERTEX_SHADER);

            String fragmentShaderCode = readClasspathFile(classLoader, fragmentShaderFile);
            Shader fragmentShader = shaderService.compileShader(fragmentShaderCode, programTextId + "_fragment", GL_FRAGMENT_SHADER);

            String geometryShaderCode = readClasspathFile(classLoader, geometryShaderFile);
            Shader geometryShader = shaderService.compileShader(geometryShaderCode, programTextId + "_geometry", GL_GEOMETRY_SHADER);

            shaderService.createShaderProgram(vertexShader, fragmentShader, geometryShader, programTextId);

            shaderService.removeShader(fragmentShader);
            shaderService.removeShader(vertexShader);
            shaderService.removeShader(geometryShader);
        } catch (IOException e) {
            throw new IllegalStateException("Default shader program file not found in classpath", e);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
