package dev.peytob.rpg.client.module.graphic.service.vendor;

import dev.peytob.rpg.client.module.graphic.resource.Mesh;
import dev.peytob.rpg.client.module.graphic.resource.VertexArray;

import java.nio.ByteBuffer;
import java.util.Collection;

public interface MeshService {

    Mesh createMesh(String textId, ByteBuffer vertices, int verticesCount, Collection<VertexArray.VertexArrayAttribute> attributes);

    boolean removeMesh(Mesh mesh);

    Mesh getMeshById(Integer meshId);

    Mesh getMeshByTextId(String meshId);
}
