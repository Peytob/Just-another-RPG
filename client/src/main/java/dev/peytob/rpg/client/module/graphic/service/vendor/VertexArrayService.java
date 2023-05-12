package dev.peytob.rpg.client.module.graphic.service.vendor;

import dev.peytob.rpg.client.module.graphic.resource.VertexArray;

import java.util.Collection;

public interface VertexArrayService {

    VertexArray createVertexArray(String id);

    boolean removeVertexArray(VertexArray vertexArray);

    void enableVertexAttributes(Collection<VertexArray.VertexArrayAttribute> defaultVertexArrayAttributes);
}
