package dev.peytob.rpg.client.service.graphic;

import dev.peytob.rpg.client.resource.Buffer;
import dev.peytob.rpg.client.resource.Mesh;
import dev.peytob.rpg.client.resource.VertexArray;
import dev.peytob.rpg.engine.repositry.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static org.lwjgl.opengl.GL33.*;

@Service
public class OpenGlMeshService implements MeshService {

    private static final Logger logger = LoggerFactory.getLogger(OpenGlMeshService.class);

    private static final Collection<VertexArray.VertexArrayAttribute> DEFAULT_ATTRIBUTES = List.of(
            new VertexArray.VertexArrayAttribute(0, 2, GL_FLOAT, false, 2 * Float.BYTES, 0L));

    private final VertexArrayService vertexArrayService;

    private final GraphicBufferService graphicBufferService;

    private final Repository<Mesh> meshRepository;

    public OpenGlMeshService(VertexArrayService vertexArrayService, GraphicBufferService graphicBufferService, Repository<Mesh> meshRepository) {
        this.vertexArrayService = vertexArrayService;
        this.graphicBufferService = graphicBufferService;
        this.meshRepository = meshRepository;
    }

    @Override
    public Mesh createMesh(float[] vertices, String textId) {
        logger.info("Creating mesh with id {}", textId);

        VertexArray vertexArray = vertexArrayService.createVertexArray(textId + "_vertexArray");
        Buffer vertexBuffer = graphicBufferService.createBuffer(textId + "_vbo", GL_ARRAY_BUFFER);

        glBindVertexArray(vertexArray.id());

        glBindBuffer(vertexBuffer.target(), vertexBuffer.id());
        graphicBufferService.updateBufferData(vertexBuffer, vertices, GL_STATIC_DRAW);

        vertexArrayService.enableVertexAttributes(DEFAULT_ATTRIBUTES);

        logger.info("Mesh with id {} created and bound to vertex array id {}", textId, vertexArray.id());

        Mesh mesh = new Mesh(
                vertexArray.id(), // Now ID equals to vertex array
                textId,
                vertexArray,
                vertexBuffer,
                vertices.length / 2); // TODO Make 2 not hardcoded...

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
}
