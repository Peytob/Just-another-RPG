package dev.peytob.rpg.client.module.graphic.model;

import dev.peytob.rpg.client.module.graphic.resource.Mesh;
import dev.peytob.rpg.math.vector.Vec2i;

public record TilemapRenderChunk(
    Mesh mesh,
    Vec2i position,
    Vec2i sizes
) {
}
