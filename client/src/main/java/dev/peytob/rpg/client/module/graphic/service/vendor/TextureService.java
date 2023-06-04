package dev.peytob.rpg.client.module.graphic.service.vendor;

import dev.peytob.rpg.client.module.graphic.resource.Texture;
import dev.peytob.rpg.math.vector.Vec2i;

import java.nio.ByteBuffer;

public interface TextureService {

    void createTexture(String textId, Integer format, Integer dataType, Vec2i sizes, ByteBuffer data);

    boolean removeTexture(Texture texture);
}
