package dev.peytob.rpg.client.graphic.resource;

import dev.peytob.rpg.engine.resource.Resource;
import dev.peytob.rpg.math.geometry.RectI;

public class Sprite implements Resource {

    private final String id;

    private Texture texture;

    private RectI normalizedRect;

    public Sprite(String id, Texture texture, RectI normalizedRect) {
        this.id = id;
        this.texture = texture;
        this.normalizedRect = normalizedRect;
    }

    public Texture getTexture() {
        return texture;
    }

    public String id() {
        return this.id;
    }

    public RectI getNormalizedRect() {
        return normalizedRect;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setNormalizedRect(RectI normalizedRect) {
        this.normalizedRect = normalizedRect;
    }
}
