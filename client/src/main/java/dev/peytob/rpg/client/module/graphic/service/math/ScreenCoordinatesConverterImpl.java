package dev.peytob.rpg.client.module.graphic.service.math;

import dev.peytob.rpg.math.vector.Vec2;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.math.vector.Vectors.immutableVec2;

@Component
public class ScreenCoordinatesConverterImpl implements ScreenCoordinatesConverter {

    /**
     * Implements next transformations, where Tw, Th - tile width and height, (X, Y) - world coordinates:
     * X(0.5Tw, 0.25Th) + Y(-0.5Tw, 0.25Th).
     * This can be calculated by appending scale and rotation matrix to (X, Y) vector.
     */
    @Override
    public Vec2 toScreenCoordinates(float x, float y, float tileSizeX, float tileSizeY) {
        return immutableVec2(
            (x - y) * (0.5f * tileSizeX),
            (x + y) * (0.5f * tileSizeY)
        );
    }

    /**
     * Implements inversion transformations for toScreenCoordinates transformations.
     */
    @Override
    public Vec2 fromScreenCoordinates(float x, float y, float tileSizeX, float tileSizeY) {
        return immutableVec2(
            x / tileSizeX + y / tileSizeY,
            y / tileSizeY - x / tileSizeX
        );
    }
}
