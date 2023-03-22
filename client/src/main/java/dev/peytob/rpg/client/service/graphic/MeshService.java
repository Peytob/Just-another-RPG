package dev.peytob.rpg.client.service.graphic;

import dev.peytob.rpg.client.resource.Mesh;

public interface MeshService {

    Mesh createMesh(float[] vertices, String textId);

    boolean removeMesh(Mesh mesh);

    Mesh getMeshById(Integer meshId);

    Mesh getMeshByTextId(String meshId);
}
