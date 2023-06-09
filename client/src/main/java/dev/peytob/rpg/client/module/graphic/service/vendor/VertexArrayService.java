package dev.peytob.rpg.client.module.graphic.service.vendor;

import dev.peytob.rpg.client.module.graphic.resource.VertexArray;

import java.util.Collection;

public interface VertexArrayService {

    VertexArray createVertexArray(String id, Collection<VertexArray.VertexArrayAttribute> tilemapBufferAttributes);

    boolean removeVertexArray(VertexArray vertexArray);
}
