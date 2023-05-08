package dev.peytob.rpg.client.module.graphic.service.facade;

import dev.peytob.rpg.client.module.graphic.resource.ShaderProgram;
import dev.peytob.rpg.client.module.graphic.service.vendor.ShaderService;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.client.module.graphic.model.DefaultShaderProgramsIds.TILEMAP_SHADER_PROGRAM_TEXT_ID;

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
