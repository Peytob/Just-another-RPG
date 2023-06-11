package dev.peytob.rpg.client.module.graphic.model;

import dev.peytob.rpg.client.module.graphic.resource.Texture;
import dev.peytob.rpg.math.geometry.Rect;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TextureAtlas {

    private final Texture texture;

    private final Map<String, Sprite> sprites;

    private TextureAtlas(Texture texture, Map<String, Sprite> sprites) {
        this.texture = texture;
        this.sprites = Collections.unmodifiableMap(sprites);
    }

    public Texture getTexture() {
        return texture;
    }

    public Sprite getSpriteByEntityId(String entityId) {
        return sprites.get(entityId);
    }

    public static class TextureAtlasBuilder {

        private final Texture texture;

        private final Map<String, Sprite> sprites;

        public TextureAtlasBuilder(Texture texture) {
            this.texture = texture;
            this.sprites = new HashMap<>();
        }

        public void appendSprite(String entityId, Rect normalizedRectangle) {
            sprites.put(entityId, new Sprite(normalizedRectangle));
        }

        public TextureAtlas build() {
            return new TextureAtlas(texture, sprites);
        }
    }
}
