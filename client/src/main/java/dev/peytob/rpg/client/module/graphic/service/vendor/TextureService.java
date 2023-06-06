package dev.peytob.rpg.client.module.graphic.service.vendor;

import dev.peytob.rpg.client.module.graphic.model.Image;
import dev.peytob.rpg.client.module.graphic.resource.Texture;
import dev.peytob.rpg.client.module.graphic.resource.TextureAtlas;
import dev.peytob.rpg.math.vector.Vec2i;

import java.nio.ByteBuffer;
import java.util.Map;

public interface TextureService {

    Texture createTexture(String textId, Integer format, Vec2i sizes, ByteBuffer data);

    default Texture createTexture(String textId, Integer format, Image image) {
        return createTexture(textId, format, image.sizes(), image.data());
    }

    boolean removeTexture(Texture texture);

    TextureAtlas wrapTextureAsAtlas(String textId, Texture texture, Map<String, TextureAtlas.Sprite> sprites);
}
