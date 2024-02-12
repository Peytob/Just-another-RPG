package dev.peytob.rpg.client.loading.event.handler;

import dev.peytob.rpg.client.fsm.event.instance.BeforeInitializingPipelineExecuteEvent;
import dev.peytob.rpg.client.graphic.model.opengl.ShaderType;
import dev.peytob.rpg.client.graphic.resource.Shader;
import dev.peytob.rpg.client.graphic.resource.ShaderProgram;
import dev.peytob.rpg.client.graphic.service.ShaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;

import static dev.peytob.rpg.client.graphic.model.DefaultShaderPrograms.TILEMAP_SHADER_PROGRAM_ID;

/**
 * Cant be called from InitializingPipeline, because initializing pipeline is called async.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class LoadDefaultShaderProgramsInitializingStep {

    private final ShaderService shaderService;

    private final ResourceLoader resourceLoader;

    @EventListener
    public void execute(BeforeInitializingPipelineExecuteEvent ignored) {
        loadShaderProgram(TILEMAP_SHADER_PROGRAM_ID, "/shaders/tilemap.vert", "/shaders/tilemap.frag");
    }

    private ShaderProgram loadShaderProgram(String id, String vertexShaderLocation, String fragmentShaderLocation) {
        Shader vertexShader = loadShader(id, vertexShaderLocation, ShaderType.VERTEX_SHADER);
        Shader fragmentShader = loadShader(id, fragmentShaderLocation, ShaderType.FRAGMENT_SHADER);
        ShaderProgram shaderProgram = shaderService.createShaderProgram(id, vertexShader, fragmentShader, null);
        shaderService.removeShader(vertexShader);
        shaderService.removeShader(fragmentShader);
        return shaderProgram;
    }

    private Shader loadShader(String shaderProgramId, String location, ShaderType shaderType) {
        String shaderSource = loadFile(location);
        String shaderId = shaderProgramId + "_" + shaderType.name();
        return shaderService.compileShader(shaderId, shaderSource, shaderType);
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
