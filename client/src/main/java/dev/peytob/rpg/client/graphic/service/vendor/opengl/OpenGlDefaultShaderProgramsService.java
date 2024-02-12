package dev.peytob.rpg.client.graphic.service.vendor.opengl;

import dev.peytob.rpg.client.graphic.resource.ShaderProgram;
import dev.peytob.rpg.client.graphic.service.DefaultShaderProgramsService;
import dev.peytob.rpg.client.graphic.service.ShaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static dev.peytob.rpg.client.graphic.model.DefaultShaderPrograms.TILEMAP_SHADER_PROGRAM_ID;

@Service
@RequiredArgsConstructor
public class OpenGlDefaultShaderProgramsService implements DefaultShaderProgramsService {

    private final ShaderService shaderService;

    @Override
    public ShaderProgram getTilemapShaderProgram() {
        return shaderService.getShaderProgramById(TILEMAP_SHADER_PROGRAM_ID)
            .orElseThrow(() -> createNotFoundException(TILEMAP_SHADER_PROGRAM_ID));
    }

    private RuntimeException createNotFoundException(String shaderProgramId) {
        return new IllegalStateException("Shader program with id " + shaderProgramId + " not found");
    }
}
