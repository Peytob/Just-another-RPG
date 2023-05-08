package dev.peytob.rpg.client.module.graphic.service.vendor;

import dev.peytob.rpg.client.module.graphic.resource.Shader;
import dev.peytob.rpg.client.module.graphic.resource.ShaderProgram;

public interface ShaderService {

    Shader compileShader(String code, String textId, int type);

    ShaderProgram createShaderProgram(Shader vertexShader, Shader fragmentShader, String textId);

    boolean removeShader(Shader shader);

    boolean removeShaderProgram(ShaderProgram shaderProgram);

    ShaderProgram getShaderProgramById(String textId);

    ShaderProgram getShaderProgramById(Integer id);
}
