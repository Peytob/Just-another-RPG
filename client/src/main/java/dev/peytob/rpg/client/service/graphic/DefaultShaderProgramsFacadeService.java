package dev.peytob.rpg.client.service.graphic;

import dev.peytob.rpg.client.resource.ShaderProgram;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.client.model.resource.graphic.DefaultShaderProgramsIds.TILEMAP_SHADER_PROGRAM_TEXT_ID;

@Component
public final class DefaultShaderProgramsFacadeService implements DefaultShaderProgramsService {

    private final ShaderService shaderService;

    public DefaultShaderProgramsFacadeService(ShaderService shaderService) {
        this.shaderService = shaderService;
    }

    @Override
    public ShaderProgram getTilemapShaderProgram() {
        return shaderService.getShaderProgramById(TILEMAP_SHADER_PROGRAM_TEXT_ID);
    }
}
