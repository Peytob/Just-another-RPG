package dev.peytob.rpg.client.graphic.service.vendor.opengl;

import dev.peytob.rpg.client.graphic.model.opengl.BufferTarget;
import dev.peytob.rpg.client.graphic.repository.MeshRepository;
import dev.peytob.rpg.client.graphic.resource.GraphicBuffer;
import dev.peytob.rpg.client.graphic.resource.Mesh;
import dev.peytob.rpg.client.graphic.resource.VertexArray;
import dev.peytob.rpg.client.graphic.resource.VertexArray.VertexArrayAttribute;
import dev.peytob.rpg.client.graphic.service.GraphicBufferService;
import dev.peytob.rpg.client.graphic.service.MeshService;
import dev.peytob.rpg.client.graphic.service.VertexArrayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Optional;

import static org.lwjgl.opengl.GL33.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenGlMeshService implements MeshService {

    private final VertexArrayService vertexArrayService;

    private final GraphicBufferService graphicBufferService;

    private final MeshRepository meshRepository;

    @Override
    public Mesh createMesh(String id, ByteBuffer buffer, Collection<VertexArrayAttribute> vertexArrayAttributes, int verticesCount) {
        log.debug("Creating mesh with id {}", id);

        VertexArray vertexArray = vertexArrayService.createVertexArray(id + "_vertexArray");
        GraphicBuffer vertexBuffer = graphicBufferService.createBuffer(id + "_vbo", BufferTarget.ARRAY_BUFFER);

        glBindVertexArray(vertexArray.vendorId());

        glBindBuffer(vertexBuffer.target().getGlTarget(), vertexBuffer.vendorId());
        graphicBufferService.updateBufferData(vertexBuffer, buffer, GL_STATIC_DRAW);

        vertexArrayService.enableVertexAttributes(vertexArrayAttributes);

        log.debug("Mesh with id {} created and bound to vertex array id {}", id, vertexArray.vendorId());

        Mesh mesh = new Mesh(
            id,
            vertexBuffer,
            vertexArray,
            verticesCount);

        meshRepository.append(mesh);

        glBindVertexArray(0);

        return mesh;
    }

    @Override
    public boolean removeMesh(Mesh mesh) {
        log.debug("Removing mesh with id {}", mesh.id());

        vertexArrayService.removeVertexArray(mesh.vertexArray());
        graphicBufferService.removeBuffer(mesh.vertexBufferObject());

        log.debug("Removed mesh with id {}", mesh.id());
        return meshRepository.remove(mesh);
    }

    @Override
    public Optional<Mesh> getMeshById(String meshId) {
        return meshRepository.getById(meshId);
    }
}
