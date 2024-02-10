package dev.peytob.rpg.client.graphic.service;

import dev.peytob.rpg.client.graphic.resource.VertexArray;

import java.util.Collection;

public interface VertexArrayService {

    VertexArray createVertexArray(String id);

    boolean removeVertexArray(VertexArray vertexArray);

    void enableVertexAttributes(Collection<VertexArray.VertexArrayAttribute> vertexArrayAttributes);
}
