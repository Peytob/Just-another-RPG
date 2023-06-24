package dev.peytob.rpg.client.module.graphic.service.vendor;

import dev.peytob.rpg.client.module.graphic.exception.ShaderCompilationException;
import dev.peytob.rpg.client.module.graphic.exception.ShaderProgramLinkException;
import dev.peytob.rpg.client.module.graphic.repository.ShaderProgramRepository;
import dev.peytob.rpg.client.module.graphic.repository.ShaderRepository;
import dev.peytob.rpg.client.module.graphic.resource.Shader;
import dev.peytob.rpg.client.module.graphic.resource.ShaderProgram;
import org.lwjgl.BufferUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.IntBuffer;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.lwjgl.opengl.GL33.*;

@Service
public final class OpenGlShaderService implements ShaderService {

    private final static Logger logger = LoggerFactory.getLogger(OpenGlShaderService.class);

    private final ShaderRepository shaderRepository;

    private final ShaderProgramRepository shaderProgramRepository;

    public OpenGlShaderService(ShaderRepository shaderRepository, ShaderProgramRepository shaderProgramRepository) {
        this.shaderRepository = shaderRepository;
        this.shaderProgramRepository = shaderProgramRepository;
    }

    @Override
    public Shader compileShader(String code, String textId, int type) {
        logger.debug("Compiling shader with id {}", textId);

        int shaderId = glCreateShader(type);
        glShaderSource(shaderId, code);
        glCompileShader(shaderId);

        int compileStatus = glGetShaderi(shaderId, GL_COMPILE_STATUS);

        if (compileStatus != GL_TRUE) {
            String shaderInfoLog = glGetShaderInfoLog(shaderId);
            glDeleteShader(shaderId);
            throw new ShaderCompilationException(shaderInfoLog);
        }

        logger.debug("Compiled shader with id {} ({})", textId, shaderId);

        Shader shader = new Shader(shaderId, textId, type);
        shaderRepository.append(shader);
        return shader;
    }

    @Override
    public ShaderProgram createShaderProgram(Shader vertexShader, Shader fragmentShader, String textId) {
        logger.debug("Creating new shader program with id {}", textId);

        assert vertexShader.type() == GL_VERTEX_SHADER;
        assert fragmentShader.type() == GL_FRAGMENT_SHADER;

        int shaderProgramId = glCreateProgram();

        logger.debug("Attaching vertex shader with id {} ({}) to shader program {} ({})",
            vertexShader.textId(), vertexShader.id(), textId, shaderProgramId);
        glAttachShader(shaderProgramId, vertexShader.id());

        logger.debug("Attaching fragment shader with id {} ({}) to shader program {} ({})",
            vertexShader.textId(), vertexShader.id(), textId, shaderProgramId);
        glAttachShader(shaderProgramId, fragmentShader.id());

        logger.debug("Linking shader program with id {} ({})", textId, shaderProgramId);
        glLinkProgram(shaderProgramId);

        int programLinkStatus = glGetProgrami(shaderProgramId, GL_LINK_STATUS);
        if (programLinkStatus != GL_TRUE) {
            String infoLog = glGetProgramInfoLog(shaderProgramId);
            glDeleteProgram(shaderProgramId);
            throw new ShaderProgramLinkException(infoLog);
        }

        logger.debug("Created shader program with id {} ({})", textId, shaderProgramId);
        ShaderProgram shaderProgram = new ShaderProgram(shaderProgramId, textId, searchUniforms(shaderProgramId));
        shaderProgramRepository.append(shaderProgram);
        return shaderProgram;
    }

    @Override
    public boolean removeShader(Shader shader) {
        logger.debug("Removing shader with id {} ({})", shader.textId(), shader.id());

        if (!shaderRepository.contains(shader.id())) {
            logger.warn("Shader with id {} ({}) not found while removing", shader.textId(), shader.id());
            return false;
        }

        Shader shaderFromRepository = shaderRepository.getById(shader.id());
        glDeleteShader(shader.id());

        logger.debug("Removed shader with id {} ({})", shader.textId(), shader.id());
        return shaderRepository.remove(shaderFromRepository);
    }

    @Override
    public boolean removeShaderProgram(ShaderProgram shaderProgram) {
        logger.debug("Removing shader program with id {} ({})", shaderProgram.textId(), shaderProgram.id());

        if (!shaderProgramRepository.contains(shaderProgram.id())) {
            logger.warn("Shader with id {} ({}) not found while removing", shaderProgram.textId(), shaderProgram.id());
            return false;
        }

        ShaderProgram shaderProgramFromRepository = shaderProgramRepository.getById(shaderProgram.id());
        logger.debug("Removed shader with id {} ({})", shaderProgram.textId(), shaderProgram.id());
        return shaderProgramRepository.remove(shaderProgramFromRepository);
    }

    @Override
    public ShaderProgram getShaderProgramById(String textId) {
        return shaderProgramRepository.getById(textId);
    }

    @Override
    public ShaderProgram getShaderProgramById(Integer id) {
        return shaderProgramRepository.getById(id);
    }

    private Map<String, ShaderProgram.ShaderProgramUniform> searchUniforms(int id) {
        int totalUniforms = glGetProgrami(id, GL_ACTIVE_UNIFORMS);
        IntBuffer sizeBuffer = BufferUtils.createIntBuffer(totalUniforms);
        IntBuffer typeBuffer = BufferUtils.createIntBuffer(totalUniforms);

        return IntStream
            .range(0, totalUniforms)
            .mapToObj(index -> {
                String uniformName = glGetActiveUniform(id, index, sizeBuffer, typeBuffer);
                int uniformLocation = glGetUniformLocation(id, uniformName);
                return new ShaderProgram.ShaderProgramUniform(uniformName, uniformLocation, sizeBuffer.get(), typeBuffer.get());
            })
            .collect(Collectors.toUnmodifiableMap(ShaderProgram.ShaderProgramUniform::name, Function.identity()));
    }
}
