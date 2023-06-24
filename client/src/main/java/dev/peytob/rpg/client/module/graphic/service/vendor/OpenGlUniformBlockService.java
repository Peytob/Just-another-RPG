package dev.peytob.rpg.client.module.graphic.service.vendor;

import dev.peytob.rpg.client.module.graphic.repository.UniformBlockRepository;
import dev.peytob.rpg.client.module.graphic.resource.Buffer;
import dev.peytob.rpg.client.module.graphic.resource.UniformBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static org.lwjgl.opengl.GL31.*;

@Component
public class OpenGlUniformBlockService implements UniformBlockService {

    private static final Logger logger = LoggerFactory.getLogger(OpenGlUniformBlockService.class);

    private final UniformBlockRepository uniformBlockRepository;

    private final GraphicBufferService graphicBufferService;

    public OpenGlUniformBlockService(UniformBlockRepository uniformBlockRepository, GraphicBufferService graphicBufferService) {
        this.uniformBlockRepository = uniformBlockRepository;
        this.graphicBufferService = graphicBufferService;
    }

    @Override
    public UniformBlock createUniformBlock(String textId, String uniformBlockName, int sizesInBytes, int bindingIndex) {
        logger.debug("Creating new uniform block with id {}", textId);

        Buffer buffer = graphicBufferService.createBuffer(textId + "_buffer", GL_UNIFORM_BUFFER);
        UniformBlock uniformBlock = new UniformBlock(
            buffer.id(),
            textId,
            sizesInBytes,
            bindingIndex,
            uniformBlockName,
            buffer);

        glBindBuffer(GL_UNIFORM_BUFFER, buffer.id());
        glBufferData(GL_UNIFORM_BUFFER, sizesInBytes, GL_STATIC_DRAW);
        glBindBuffer(GL_UNIFORM_BUFFER, 0);

        glBindBufferRange(GL_UNIFORM_BUFFER, bindingIndex, buffer.id(), 0, sizesInBytes);

        uniformBlockRepository.append(uniformBlock);

        logger.debug("Created new uniform block with id {} ({})", textId, uniformBlock.buffer().id());
        return uniformBlock;
    }

    @Override
    public boolean removeUniformBlock(UniformBlock uniformBlock) {
        logger.debug("Removing uniform block with id {} ({})", uniformBlock.textId(), uniformBlock.id());

        glBindBuffer(GL_UNIFORM_BUFFER, 0);

        if (!uniformBlockRepository.contains(uniformBlock.id())) {
            logger.warn("Uniform block with id {} ({}) not found while removing", uniformBlock.textId(), uniformBlock.id());
            return false;
        }

        UniformBlock uniformBlockFromRepository = uniformBlockRepository.getById(uniformBlock.id());
        graphicBufferService.removeBuffer(uniformBlockFromRepository.buffer());
        uniformBlockRepository.remove(uniformBlockFromRepository);

        return true;
    }
}
