package dev.peytob.rpg.client.module.graphic.service.vendor;

import dev.peytob.rpg.client.module.graphic.repository.BufferRepository;
import dev.peytob.rpg.client.module.graphic.resource.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static org.lwjgl.opengl.GL33.glDeleteBuffers;
import static org.lwjgl.opengl.GL33.glGenBuffers;

@Service
public final class OpenGlGraphicBufferService implements GraphicBufferService {

    private static final Logger logger = LoggerFactory.getLogger(OpenGlGraphicBufferService.class);

    private final BufferRepository bufferRepository;

    public OpenGlGraphicBufferService(BufferRepository bufferRepository) {
        this.bufferRepository = bufferRepository;
    }

    @Override
    public Buffer createBuffer(String textId, int target) {
        logger.trace("Creating new buffer with id {}", textId);

        int bufferId = glGenBuffers();
        Buffer buffer = new Buffer(bufferId, textId, target);
        bufferRepository.append(buffer);

        logger.trace("Created new buffer with id {} ({})", textId, bufferId);
        return buffer;
    }

    @Override
    public boolean removeBuffer(Buffer buffer) {
        logger.trace("Removing buffer with id {} ({})", buffer.textId(), buffer.id());

        if (!bufferRepository.contains(buffer.id())) {
            logger.warn("Buffer with id {} ({}) not found while removing", buffer.textId(), buffer.id());
            return false;
        }

        Buffer bufferFromRepository = bufferRepository.getById(buffer.id());
        glDeleteBuffers(bufferFromRepository.id());

        logger.trace("Removed buffer with id {} ({})", buffer.textId(), buffer.id());
        return bufferRepository.remove(bufferFromRepository);
    }
}
