package dev.peytob.rpg.client.service.graphic;

import dev.peytob.rpg.client.resource.VertexArray;
import dev.peytob.rpg.engine.repositry.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static org.lwjgl.opengl.GL33.*;

@Service
public class OpenGlVertexArrayService implements VertexArrayService {

    private static final Logger logger = LoggerFactory.getLogger(OpenGlVertexArrayService.class);

    private final Repository<VertexArray> vertexArrayRepository;

    public OpenGlVertexArrayService(Repository<VertexArray> vertexArrayRepository) {
        this.vertexArrayRepository = vertexArrayRepository;
    }

    @Override
    public VertexArray createVertexArray(String id) {
        logger.info("Creating new vertex array with id {}", id);

        int vertexArrayId = glGenVertexArrays();
        VertexArray vertexArray = new VertexArray(vertexArrayId, id);
        vertexArrayRepository.append(vertexArray);

        logger.info("Created new vertex array with id {}", id);
        return vertexArray;
    }

    @Override
    public boolean removeVertexArray(VertexArray vertexArray) {
        logger.info("Removing vertex array with id {} ({})", vertexArray.textId(), vertexArray.id());

        if (!vertexArrayRepository.contains(vertexArray.id())) {
            logger.warn("Vertex array with id {} ({})", vertexArray.textId(), vertexArray.id());
            return false;
        }

        VertexArray vertexArrayFromRepository = vertexArrayRepository.getById(vertexArray.id());
        glDeleteVertexArrays(vertexArrayFromRepository.id());

        logger.info("Removed vertex array with id {} ({})", vertexArray.textId(), vertexArray.id());
        return vertexArrayRepository.remove(vertexArrayFromRepository);
    }

    @Override
    public void enableVertexAttributes(Collection<VertexArray.VertexArrayAttribute> defaultVertexArrayAttributes) {
        defaultVertexArrayAttributes.forEach(attribute -> {
            glVertexAttribPointer(
                    attribute.index(),
                    attribute.size(),
                    attribute.type(),
                    attribute.normalized(),
                    attribute.stride(),
                    attribute.offset());
            glEnableVertexAttribArray(attribute.index());
        });
    }
}
