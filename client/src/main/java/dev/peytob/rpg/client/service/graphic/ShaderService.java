package dev.peytob.rpg.client.service.graphic;

import dev.peytob.rpg.client.resource.Shader;
import dev.peytob.rpg.client.resource.ShaderProgram;

public interface ShaderService {

    Shader compileShader(String code, String textId, int type);

    ShaderProgram createShaderProgram(Shader vertexShader, Shader fragmentShader, String textId);

    boolean removeShader(Shader shader);

    boolean removeShaderProgram(ShaderProgram shaderProgram);

    ShaderProgram getShaderProgramById(String textId);

    ShaderProgram getShaderProgramById(Integer id);
}
