package dev.peytob.rpg.client.service.graphic;

import dev.peytob.rpg.client.resource.Buffer;

public interface GraphicBufferService {

    Buffer createBuffer(String textId, int target);

    boolean removeBuffer(Buffer buffer);
}
