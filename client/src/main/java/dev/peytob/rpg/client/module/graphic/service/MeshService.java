package dev.peytob.rpg.client.module.graphic.service;

import dev.peytob.rpg.client.module.graphic.resource.Mesh;

public interface MeshService {

    Mesh createMesh(float[] vertices, String textId);

    boolean removeMesh(Mesh mesh);

    Mesh getMeshById(Integer meshId);

    Mesh getMeshByTextId(String meshId);
}
