package dev.peytob.rpg.client.module.graphic.service.facade.shaderprogram;

import dev.peytob.rpg.client.module.graphic.repository.ShaderProgramRepository;
import dev.peytob.rpg.client.module.graphic.resource.ShaderProgram;
import dev.peytob.rpg.math.vector.Vec2i;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.client.module.graphic.model.DefaultShaderProgramsIds.TILEMAP_SHADER_PROGRAM_TEXT_ID;
import static org.lwjgl.opengl.GL31.*;

@Component
public class TilemapShaderProgramFacadeImpl implements TilemapShaderProgramFacade {

    private static final String MAP_SIZE_TILES_UNIFORM = "u_mapSizeTiles";
    private static final String TILE_SIZE_TILES_UNIFORM = "u_tileSizesPixels";

    private static final Logger logger = LoggerFactory.getLogger(TilemapShaderProgramFacadeImpl.class);

    private final ShaderProgramRepository shaderProgramRepository;

    public TilemapShaderProgramFacadeImpl(ShaderProgramRepository shaderProgramRepository) {
        this.shaderProgramRepository = shaderProgramRepository;
    }

    @Override
    public void setTilemapSizes(Vec2i sizes) {
        ShaderProgram shaderProgram = getTilemapShaderProgram();
        ShaderProgram.ShaderProgramUniform tilemapSizesUniform = shaderProgram.uniformLocations().get(MAP_SIZE_TILES_UNIFORM);

        if (tilemapSizesUniform == null) {
            logger.error("{} uniform not found in tilemap shader", MAP_SIZE_TILES_UNIFORM);
            return;
        }

        glUseProgram(shaderProgram.id());
        glUniform2i(tilemapSizesUniform.location(), sizes.x(), sizes.y());
        glUseProgram(0);
    }

    @Override
    public void setTileSizes(Vec2i sizes) {
        ShaderProgram shaderProgram = getTilemapShaderProgram();
        ShaderProgram.ShaderProgramUniform tilemapSizesUniform = shaderProgram.uniformLocations().get(TILE_SIZE_TILES_UNIFORM);

        if (tilemapSizesUniform == null) {
            logger.error("{} uniform not found in tilemap shader", TILE_SIZE_TILES_UNIFORM);
            return;
        }

        glUseProgram(shaderProgram.id());
        glUniform2i(tilemapSizesUniform.location(), sizes.x(), sizes.y());
        glUseProgram(0);
    }

    @Override
    public ShaderProgram getTilemapShaderProgram() {
        return shaderProgramRepository.getById(TILEMAP_SHADER_PROGRAM_TEXT_ID);
    }
}
