package dev.peytob.rpg.client.module.graphic.service.vendor;

import dev.peytob.rpg.client.module.graphic.resource.Buffer;

import java.nio.ByteBuffer;

public interface GraphicBufferService {

    Buffer createBuffer(String textId, int target);

    boolean removeBuffer(Buffer buffer);

    void updateBufferData(Buffer buffer, ByteBuffer data, int drawingMode);
}
