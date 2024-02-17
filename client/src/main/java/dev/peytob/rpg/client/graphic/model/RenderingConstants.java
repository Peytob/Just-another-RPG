package dev.peytob.rpg.client.graphic.model;

import dev.peytob.rpg.math.vector.Vec2i;

import static dev.peytob.rpg.core.gameplay.constants.PhysicsConstants.TILE_SIZE;
import static dev.peytob.rpg.math.vector.Vectors.immutableVec2i;

public enum RenderingConstants {;

    public static final int RENDERING_COEFFICIENT = 64;

    public static final Vec2i TILE_SIZE_PIXELS = immutableVec2i(TILE_SIZE.multiply(RENDERING_COEFFICIENT));
}
