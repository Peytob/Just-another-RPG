package dev.peytob.rpg.client.module.graphic.service.facade.shaderprogram;

import dev.peytob.rpg.client.module.graphic.resource.ShaderProgram;
import dev.peytob.rpg.math.vector.Vec2i;

public interface TilemapShaderProgramFacade {

    void setTilemapSizes(Vec2i sizes);

    void setTileSizes(Vec2i sizes);

    ShaderProgram getTilemapShaderProgram();
}
