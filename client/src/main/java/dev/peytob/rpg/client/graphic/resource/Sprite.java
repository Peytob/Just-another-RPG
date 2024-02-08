package dev.peytob.rpg.client.graphic.resource;

import dev.peytob.rpg.engine.resource.Resource;
import dev.peytob.rpg.math.geometry.RectI;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sprite implements Resource {

    @Getter(AccessLevel.NONE)
    private final String id;

    private Texture texture;

    private RectI normalizedRect;

    public Sprite(String id, Texture texture, RectI normalizedRect) {
        this.id = id;
        this.texture = texture;
        this.normalizedRect = normalizedRect;
    }

    public String id() {
        return this.id;
    }
}
