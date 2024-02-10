package dev.peytob.rpg.client.graphic.service;

import dev.peytob.rpg.client.graphic.resource.Mesh;
import dev.peytob.rpg.client.graphic.resource.VertexArray;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Optional;

public interface MeshService {
    Mesh createMesh(String id, ByteBuffer buffer, Collection<VertexArray.VertexArrayAttribute> vertexArrayAttributes, int verticesCount);

    boolean removeMesh(Mesh mesh);

    Optional<Mesh> getMeshById(String meshId);
}
