package dev.peytob.rpg.client.service.graphic;

import dev.peytob.rpg.client.resource.VertexArray;

import java.util.Collection;

public interface VertexArrayService {

    VertexArray createVertexArray(String id);

    boolean removeVertexArray(VertexArray vertexArray);

    void enableVertexAttributes(Collection<VertexArray.VertexArrayAttribute> defaultVertexArrayAttributes);
}
