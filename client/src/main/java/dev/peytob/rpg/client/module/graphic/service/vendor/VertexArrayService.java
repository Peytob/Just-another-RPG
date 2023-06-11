package dev.peytob.rpg.client.module.graphic.service.vendor;

import dev.peytob.rpg.client.module.graphic.resource.VertexArray;

public interface VertexArrayService {

    VertexArray createVertexArray(String id);

    boolean removeVertexArray(VertexArray vertexArray);
}
