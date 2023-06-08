package dev.peytob.rpg.client.module.graphic.service;

import dev.peytob.rpg.client.module.graphic.resource.Buffer;
import dev.peytob.rpg.client.module.graphic.resource.Mesh;
import dev.peytob.rpg.client.module.graphic.resource.VertexArray;
import dev.peytob.rpg.client.module.graphic.service.vendor.GraphicBufferService;
import dev.peytob.rpg.client.module.graphic.service.vendor.VertexArrayService;
import dev.peytob.rpg.engine.repositry.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;

import static org.lwjgl.opengl.GL33.*;

@Service
public class OpenGlMeshService implements MeshService {

    private static final Logger logger = LoggerFactory.getLogger(OpenGlMeshService.class);

    /**
     * Target shader layout:
     * layout (location = 0) in vec2 a_tilePosition;
     * layout (location = 1) in vec2 a_normalizedTextureCoordinates;
     */
    private static final Collection<VertexArray.VertexArrayAttribute> SPRITE_BUFFER_ATTRIBUTES = List.of(
        new VertexArray.VertexArrayAttribute(0, 2, GL_FLOAT, false, 2 * Float.BYTES, 0L),
        new VertexArray.VertexArrayAttribute(0, 2, GL_FLOAT, false, 2 * Float.BYTES, 8L));

    private final VertexArrayService vertexArrayService;

    private final GraphicBufferService graphicBufferService;

    private final Repository<Mesh> meshRepository;

    public OpenGlMeshService(VertexArrayService vertexArrayService, GraphicBufferService graphicBufferService, Repository<Mesh> meshRepository) {
        this.vertexArrayService = vertexArrayService;
        this.graphicBufferService = graphicBufferService;
        this.meshRepository = meshRepository;
    }

    @Override
    public Mesh createMesh(ByteBuffer buffer, int verticesCount, String textId) {
        logger.info("Creating mesh with id {}", textId);

        VertexArray vertexArray = vertexArrayService.createVertexArray(textId + "_vertexArray");
        Buffer vertexBuffer = graphicBufferService.createBuffer(textId + "_vbo", GL_ARRAY_BUFFER);

        glBindVertexArray(vertexArray.id());

        glBindBuffer(vertexBuffer.target(), vertexBuffer.id());
        graphicBufferService.updateBufferData(vertexBuffer, buffer, GL_STATIC_DRAW);

        vertexArrayService.enableVertexAttributes(SPRITE_BUFFER_ATTRIBUTES);

        logger.info("Mesh with id {} created and bound to vertex array id {}", textId, vertexArray.id());

        Mesh mesh = new Mesh(
            vertexArray.id(),
            textId,
            vertexArray,
            vertexBuffer,
            verticesCount);

        meshRepository.append(mesh);

        glBindVertexArray(0);

        return mesh;
    }

    @Override
    public boolean removeMesh(Mesh mesh) {
        logger.debug("Removing mesh with id {} ({})", mesh.textId(), mesh.id());

        if (!meshRepository.contains(mesh.id())) {
            logger.warn("Mesh with id {} ({}) not found while removing", mesh.textId(), mesh.id());
            return false;
        }

        vertexArrayService.removeVertexArray(mesh.vertexArray());
        graphicBufferService.removeBuffer(mesh.vertexBufferObject());

        logger.debug("Removed mesh with id {} ({})", mesh.textId(), mesh.id());
        return meshRepository.remove(mesh);
    }

    @Override
    public Mesh getMeshById(Integer meshId) {
        return meshRepository.getById(meshId);
    }

    @Override
    public Mesh getMeshByTextId(String meshId) {
        return meshRepository.getById(meshId);
    }
}
