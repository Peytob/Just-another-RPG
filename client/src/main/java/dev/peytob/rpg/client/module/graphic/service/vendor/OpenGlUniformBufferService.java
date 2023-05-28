package dev.peytob.rpg.client.module.graphic.service.vendor;

import dev.peytob.rpg.client.module.graphic.repository.UniformBufferRepository;
import dev.peytob.rpg.client.module.graphic.resource.Buffer;
import dev.peytob.rpg.client.module.graphic.resource.UniformBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL31.GL_UNIFORM_BUFFER;

@Component
public class OpenGlUniformBufferService implements UniformBufferService {

    private static final Logger logger = LoggerFactory.getLogger(OpenGlUniformBufferService.class);

    private final UniformBufferRepository uniformBufferRepository;

    private final GraphicBufferService graphicBufferService;

    public OpenGlUniformBufferService(UniformBufferRepository uniformBufferRepository, GraphicBufferService graphicBufferService) {
        this.uniformBufferRepository = uniformBufferRepository;
        this.graphicBufferService = graphicBufferService;
    }

    @Override
    public UniformBuffer createUniformBlock(String textId, int sizesInBytes) {
        logger.info("Creating new uniform block with id {}", textId);

        Buffer buffer = graphicBufferService.createBuffer(textId + "_buffer", GL_UNIFORM_BUFFER);
        UniformBuffer uniformBuffer = new UniformBuffer(buffer.id(), textId, sizesInBytes, buffer);

        glBindBuffer(GL_UNIFORM_BUFFER, buffer.id());
        glBufferData(GL_UNIFORM_BUFFER, sizesInBytes, GL_STATIC_DRAW);
        glBindBuffer(GL_UNIFORM_BUFFER, 0);

        uniformBufferRepository.append(uniformBuffer);

        logger.info("Created new uniform block with id {} ({})", textId, uniformBuffer.buffer().id());
        return uniformBuffer;
    }

    @Override
    public boolean removeUniformBlock(UniformBuffer uniformBuffer) {
        logger.info("Removing uniform buffer with id {} ({})", uniformBuffer.textId(), uniformBuffer.id());

        glBindBuffer(GL_UNIFORM_BUFFER, 0);

        if (!uniformBufferRepository.contains(uniformBuffer.id())) {
            logger.warn("Buffer with id {} ({}) not found while removing", uniformBuffer.textId(), uniformBuffer.id());
            return false;
        }

        UniformBuffer uniformBufferFromRepository = uniformBufferRepository.getById(uniformBuffer.id());
        graphicBufferService.removeBuffer(uniformBufferFromRepository.buffer());
        uniformBufferRepository.remove(uniformBufferFromRepository);

        return true;
    }
}
