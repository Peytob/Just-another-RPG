package dev.peytob.rpg.client.graphic.service.vendor.opengl;

import dev.peytob.rpg.client.graphic.repository.VertexArrayRepository;
import dev.peytob.rpg.client.graphic.resource.VertexArray;
import dev.peytob.rpg.client.graphic.service.VertexArrayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static org.lwjgl.opengl.GL33.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenGlVertexArrayService implements VertexArrayService {

    private final VertexArrayRepository vertexArrayRepository;

    @Override
    public VertexArray createVertexArray(String id) {
        log.info("Creating new vertex array with id {}", id);

        int vertexArrayId = glGenVertexArrays();
        VertexArray vertexArray = new VertexArray(id, vertexArrayId);
        vertexArrayRepository.append(vertexArray);

        log.info("Created new vertex array with id {}", id);
        return vertexArray;
    }

    @Override
    public boolean removeVertexArray(VertexArray vertexArray) {
        log.info("Removing vertex array with id {} ({})", vertexArray.id(), vertexArray.vendorId());
        glDeleteVertexArrays(vertexArray.vendorId());
        log.info("Removed vertex array with id {} ({})", vertexArray.id(), vertexArray.vendorId());

        return vertexArrayRepository.remove(vertexArray);
    }

    @Override
    public void enableVertexAttributes(Collection<VertexArray.VertexArrayAttribute> vertexArrayAttributes) {
        for (VertexArray.VertexArrayAttribute attribute : vertexArrayAttributes) {
            glVertexAttribPointer(
                attribute.index(),
                attribute.size(),
                attribute.type(),
                attribute.normalized(),
                attribute.stride(),
                attribute.offset());
            glEnableVertexAttribArray(attribute.index());
        }
    }
}
