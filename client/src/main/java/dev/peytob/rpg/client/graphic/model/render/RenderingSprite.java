package dev.peytob.rpg.client.graphic.model.render;

import dev.peytob.rpg.client.graphic.resource.Sprite;
import dev.peytob.rpg.math.vector.Vec2;

public record RenderingSprite(
    Sprite sprite,

    Vec2 position
) {
}
