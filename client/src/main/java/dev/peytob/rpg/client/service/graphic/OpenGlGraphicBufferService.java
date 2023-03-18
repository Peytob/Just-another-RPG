package dev.peytob.rpg.client.service.graphic;

import dev.peytob.rpg.client.resource.Buffer;
import dev.peytob.rpg.engine.repositry.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static org.lwjgl.opengl.GL33.*;

@Service
public final class OpenGlGraphicBufferService implements GraphicBufferService {

    private static final Logger logger = LoggerFactory.getLogger(OpenGlGraphicBufferService.class);

    private final Repository<Buffer> bufferRepository;

    public OpenGlGraphicBufferService(Repository<Buffer> bufferRepository) {
        this.bufferRepository = bufferRepository;
    }

    @Override
    public Buffer createBuffer(String textId, int target) {
        logger.info("Creating new buffer with id {}", textId);

        int bufferId = glGenBuffers();
        Buffer buffer = new Buffer(bufferId, textId, target);
        bufferRepository.append(buffer);

        logger.info("Created new buffer with id {} ({})", textId, bufferId);
        return buffer;
    }

    @Override
    public boolean removeBuffer(Buffer buffer) {
        logger.info("Removing buffer with id {} ({})", buffer.textId(), buffer.id());

        if (!bufferRepository.contains(buffer.id())) {
            logger.warn("Buffer with id {} ({}) not found while removing", buffer.textId(), buffer.id());
            return false;
        }

        Buffer bufferFromRepository = bufferRepository.getById(buffer.id());
        glDeleteBuffers(bufferFromRepository.id());

        logger.info("Removed buffer with id {} ({})", buffer.textId(), buffer.id());
        return bufferRepository.remove(bufferFromRepository);
    }

    @Override
    public void updateBufferData(Buffer buffer, float[] data, int usage) {
        glBufferData(buffer.id(), data, usage);
    }
}
