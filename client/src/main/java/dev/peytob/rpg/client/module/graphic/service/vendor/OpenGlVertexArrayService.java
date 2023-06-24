package dev.peytob.rpg.client.module.graphic.service.vendor;

import dev.peytob.rpg.client.module.graphic.repository.VertexArrayRepository;
import dev.peytob.rpg.client.module.graphic.resource.VertexArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static org.lwjgl.opengl.GL33.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL33.glGenVertexArrays;

@Service
public final class OpenGlVertexArrayService implements VertexArrayService {

    private static final Logger logger = LoggerFactory.getLogger(OpenGlVertexArrayService.class);

    private final VertexArrayRepository vertexArrayRepository;

    public OpenGlVertexArrayService(VertexArrayRepository vertexArrayRepository) {
        this.vertexArrayRepository = vertexArrayRepository;
    }

    @Override
    public VertexArray createVertexArray(String id) {
        logger.trace("Creating new vertex array with id {}", id);

        int vertexArrayId = glGenVertexArrays();
        VertexArray vertexArray = new VertexArray(vertexArrayId, id);
        vertexArrayRepository.append(vertexArray);

        logger.trace("Created new vertex array with id {}", id);
        return vertexArray;
    }

    @Override
    public boolean removeVertexArray(VertexArray vertexArray) {
        logger.trace("Removing vertex array with id {} ({})", vertexArray.textId(), vertexArray.id());

        if (!vertexArrayRepository.contains(vertexArray.id())) {
            logger.warn("Vertex array with id {} ({})", vertexArray.textId(), vertexArray.id());
            return false;
        }

        VertexArray vertexArrayFromRepository = vertexArrayRepository.getById(vertexArray.id());
        glDeleteVertexArrays(vertexArrayFromRepository.id());

        logger.trace("Removed vertex array with id {} ({})", vertexArray.textId(), vertexArray.id());
        return vertexArrayRepository.remove(vertexArrayFromRepository);
    }
}
