package dev.peytob.rpg.client.module.graphic.service;

import dev.peytob.rpg.client.module.graphic.resource.Mesh;

import java.nio.ByteBuffer;

public interface MeshService {

    Mesh createMesh(ByteBuffer buffer, int verticesCount, String textId);

    boolean removeMesh(Mesh mesh);

    Mesh getMeshById(Integer meshId);

    Mesh getMeshByTextId(String meshId);
}
