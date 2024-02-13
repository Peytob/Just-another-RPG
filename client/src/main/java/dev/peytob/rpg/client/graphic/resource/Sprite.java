package dev.peytob.rpg.client.graphic.resource;

import dev.peytob.rpg.engine.resource.Resource;
import dev.peytob.rpg.math.geometry.Rect;
import dev.peytob.rpg.math.vector.Vec2;
import dev.peytob.rpg.math.vector.Vec2i;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sprite implements Resource {

    @Getter(AccessLevel.NONE)
    private final String id;

    private Texture texture;

    private Rect normalizedTextureRect;

    public Sprite(String id, Texture texture, Rect normalizedTextureRect) {
        this.id = id;
        this.texture = texture;
        this.normalizedTextureRect = normalizedTextureRect;
    }

    public String id() {
        return this.id;
    }
}
