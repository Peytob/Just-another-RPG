package dev.peytob.rpg.client.graphic.service.vendor.opengl;

import dev.peytob.rpg.client.graphic.exception.ShaderCompilationException;
import dev.peytob.rpg.client.graphic.exception.ShaderProgramLinkException;
import dev.peytob.rpg.client.graphic.model.opengl.ShaderType;
import dev.peytob.rpg.client.graphic.repository.ShaderProgramRepository;
import dev.peytob.rpg.client.graphic.repository.ShaderRepository;
import dev.peytob.rpg.client.graphic.resource.Shader;
import dev.peytob.rpg.client.graphic.resource.ShaderProgram;
import dev.peytob.rpg.client.graphic.service.ShaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lwjgl.BufferUtils;
import org.springframework.stereotype.Service;

import java.nio.IntBuffer;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static dev.peytob.rpg.client.graphic.model.opengl.ShaderType.*;
import static java.util.function.Function.identity;
import static org.lwjgl.opengl.GL33.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenGlShaderService implements ShaderService {

    private final ShaderRepository shaderRepository;

    private final ShaderProgramRepository shaderProgramRepository;

    @Override
    public Shader compileShader(String id, String code, ShaderType type) {
        log.info("Compiling shader with id {} and type {}", id, type);

        int shaderVendorId = glCreateShader(type.getGlType());
        glShaderSource(shaderVendorId, code);
        glCompileShader(shaderVendorId);

        int compileStatus = glGetShaderi(shaderVendorId, GL_COMPILE_STATUS);

        if (compileStatus != GL_TRUE) {
            String shaderInfoLog = glGetShaderInfoLog(shaderVendorId);
            glDeleteShader(shaderVendorId);
            throw new ShaderCompilationException(shaderInfoLog);
        }

        log.info("Compiled shader with id {} ({})", id, shaderVendorId);

        Shader shader = new Shader(id, shaderVendorId, type);
        shaderRepository.append(shader);
        return shader;
    }

    @Override
    public ShaderProgram createShaderProgram(String id, Shader vertexShader, Shader fragmentShader, Shader geometryShader) {
        log.info("Creating new shader program with id {}", id);

        assert vertexShader.shaderType() == VERTEX_SHADER;
        assert fragmentShader.shaderType() == FRAGMENT_SHADER;
        assert geometryShader == null || geometryShader.shaderType() == GEOMETRY_SHADER;

        int shaderProgramVendorId = glCreateProgram();

        log.info("Attaching vertex shader with id {} ({}) to shader program {} ({})",
            vertexShader.id(), vertexShader.vendorId(), id, shaderProgramVendorId);
        glAttachShader(shaderProgramVendorId, vertexShader.vendorId());

        log.info("Attaching fragment shader with id {} ({}) to shader program {} ({})",
            fragmentShader.id(), fragmentShader.vendorId(), id, shaderProgramVendorId);
        glAttachShader(shaderProgramVendorId, fragmentShader.vendorId());

        if (geometryShader != null) {
            log.info("Attaching geometry shader with id {} ({}) to shader program {} ({})",
                geometryShader.id(), geometryShader.vendorId(), id, shaderProgramVendorId);
            glAttachShader(shaderProgramVendorId, geometryShader.vendorId());
        }

        log.info("Linking shader program with id {} ({})", id, shaderProgramVendorId);
        glLinkProgram(shaderProgramVendorId);

        int programLinkStatus = glGetProgrami(shaderProgramVendorId, GL_LINK_STATUS);
        if (programLinkStatus != GL_TRUE) {
            String infoLog = glGetProgramInfoLog(shaderProgramVendorId);
            glDeleteProgram(shaderProgramVendorId);
            throw new ShaderProgramLinkException(infoLog);
        }

        log.info("Created shader program with id {} ({})", id, shaderProgramVendorId);
        ShaderProgram shaderProgram = new ShaderProgram(id, shaderProgramVendorId, searchUniforms(shaderProgramVendorId));
        shaderProgramRepository.append(shaderProgram);
        return shaderProgram;
    }

    @Override
    public boolean removeShader(Shader shader) {
        log.info("Removing shader with id {} ({})", shader.id(), shader.vendorId());

        glDeleteShader(shader.vendorId());

        log.info("Removed shader with id {} ({})", shader.id(), shader.vendorId());

        return shaderRepository.remove(shader);
    }

    @Override
    public boolean removeShaderProgram(ShaderProgram shaderProgram) {
        return shaderProgramRepository.remove(shaderProgram);
    }

    @Override
    public Optional<ShaderProgram> getShaderProgramById(String id) {
        return shaderProgramRepository.getById(id);
    }

    private Map<String, ShaderProgram.ShaderProgramUniform> searchUniforms(int shaderProgramId) {
        int totalUniforms = glGetProgrami(shaderProgramId, GL_ACTIVE_UNIFORMS);
        IntBuffer sizeBuffer = BufferUtils.createIntBuffer(totalUniforms);
        IntBuffer typeBuffer = BufferUtils.createIntBuffer(totalUniforms);

        return IntStream
            .range(0, totalUniforms)
            .mapToObj(index -> {
                String uniformName = glGetActiveUniform(shaderProgramId, index, sizeBuffer, typeBuffer);
                int uniformLocation = glGetUniformLocation(shaderProgramId, uniformName);
                return new ShaderProgram.ShaderProgramUniform(uniformName, uniformLocation, sizeBuffer.get(), typeBuffer.get());
            })
            .collect(Collectors.toUnmodifiableMap(ShaderProgram.ShaderProgramUniform::name, identity()));
    }
}
