package dev.peytob.rpg.client.graphic.service;

import dev.peytob.rpg.client.graphic.model.opengl.ShaderType;
import dev.peytob.rpg.client.graphic.resource.Shader;
import dev.peytob.rpg.client.graphic.resource.ShaderProgram;

import java.util.Optional;

public interface ShaderService {

    Shader compileShader(String id, String code, ShaderType type);

    ShaderProgram createShaderProgram(String id, Shader vertexShader, Shader fragmentShader, Shader geometryShader);

    boolean removeShader(Shader shader);

    boolean removeShaderProgram(ShaderProgram shaderProgram);

    Optional<ShaderProgram> getShaderProgramById(String id);
}
