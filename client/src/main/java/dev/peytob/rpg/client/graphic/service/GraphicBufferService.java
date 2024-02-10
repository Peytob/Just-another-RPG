package dev.peytob.rpg.client.graphic.service;

import dev.peytob.rpg.client.graphic.model.opengl.BufferTarget;
import dev.peytob.rpg.client.graphic.resource.GraphicBuffer;

import java.nio.ByteBuffer;

public interface GraphicBufferService {
    GraphicBuffer createBuffer(String id, BufferTarget target);

    boolean removeBuffer(GraphicBuffer buffer);

    void updateBufferData(GraphicBuffer buffer, ByteBuffer data, int usage);
}
