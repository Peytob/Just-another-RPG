package dev.peytob.rpg.client.graphic.service.vendor.opengl;

import dev.peytob.rpg.client.graphic.model.opengl.BufferTarget;
import dev.peytob.rpg.client.graphic.repository.GraphicBufferRepository;
import dev.peytob.rpg.client.graphic.resource.GraphicBuffer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL33.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenGlGraphicBufferService implements dev.peytob.rpg.client.graphic.service.GraphicBufferService {

    private final GraphicBufferRepository graphicBufferRepository;

    @Override
    public GraphicBuffer createBuffer(String id, BufferTarget target) {
        log.info("Creating new buffer with id {}", id);

        int bufferId = glGenBuffers();
        GraphicBuffer buffer = new GraphicBuffer(id, bufferId, target);
        graphicBufferRepository.append(buffer);

        log.info("Created new buffer with id {} ({})", id, bufferId);
        return buffer;
    }

    @Override
    public boolean removeBuffer(GraphicBuffer buffer) {
        log.info("Removing buffer with id {} ({})", buffer.id(), buffer.vendorId());

        glDeleteBuffers(buffer.vendorId());

        log.info("Removed buffer with id {} ({})", buffer.id(), buffer.vendorId());
        return graphicBufferRepository.remove(buffer);
    }

    @Override
    public void updateBufferData(GraphicBuffer buffer, ByteBuffer data, int usage) {
        glBufferData(buffer.vendorId(), data, usage);
    }
}
