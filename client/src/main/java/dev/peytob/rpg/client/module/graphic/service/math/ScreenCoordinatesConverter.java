package dev.peytob.rpg.client.module.graphic.service.math;

import dev.peytob.rpg.math.vector.Vec2;

public interface ScreenCoordinatesConverter {

    Vec2 toScreenCoordinates(float x, float y, float tileSizeX, float tileSizeY);

    default Vec2 toScreenCoordinates(Vec2 coordinates, Vec2 tileSize) {
        return toScreenCoordinates(coordinates.x(), coordinates.y(), tileSize.x(), tileSize.y());
    }

    Vec2 fromScreenCoordinates(float x, float y, float tileSizeX, float tileSizeY);

    default Vec2 fromScreenCoordinates(Vec2 coordinates, Vec2 tileSize) {
        return fromScreenCoordinates(coordinates.x(), coordinates.y(), tileSize.x(), tileSize.y());
    }
}
