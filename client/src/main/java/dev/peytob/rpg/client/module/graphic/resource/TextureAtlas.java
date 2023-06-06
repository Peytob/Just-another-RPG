package dev.peytob.rpg.client.module.graphic.resource;

import dev.peytob.rpg.engine.resource.Resource;
import dev.peytob.rpg.math.vector.Vec2i;

import java.util.Map;

public record TextureAtlas(
    Integer id,
    String textId,
    Map<String, Sprite> tilesSprites,
    Texture texture
) implements Resource {

    public record Sprite(
        Integer spriteId,
        Vec2i atlasOffset,
        Vec2i atlasSizes
    ) {
    }
}
