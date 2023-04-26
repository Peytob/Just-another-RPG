package dev.peytob.rpg.client.service.graphic.vendor;

import dev.peytob.rpg.client.resource.Buffer;

public interface GraphicBufferService {

    Buffer createBuffer(String textId, int target);

    boolean removeBuffer(Buffer buffer);

    void updateBufferData(Buffer buffer, float[] data, int drawingMode);
}
