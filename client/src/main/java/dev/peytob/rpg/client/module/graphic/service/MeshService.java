package dev.peytob.rpg.client.module.graphic.service;

import dev.peytob.rpg.client.module.graphic.resource.Mesh;
import dev.peytob.rpg.client.module.graphic.resource.VertexArray;

import java.util.Collection;

public interface MeshService {

    Mesh createMesh(String textId, float[] vertices, Collection<VertexArray.VertexArrayAttribute> attributes);

    boolean removeMesh(Mesh mesh);

    Mesh getMeshById(Integer meshId);

    Mesh getMeshByTextId(String meshId);
}
