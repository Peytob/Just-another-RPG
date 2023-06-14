package dev.peytob.rpg.client.module.graphic.model;

import dev.peytob.rpg.math.geometry.Rect;
import dev.peytob.rpg.math.vector.Vec2;
import dev.peytob.rpg.math.vector.Vec2i;
import dev.peytob.rpg.math.vector.Vectors;

import static dev.peytob.rpg.math.geometry.Rectangles.rect;

public final class Camera {

    private Vec2i resolution;

    private Rect visionRectangle;

    public Camera(Vec2 position, Vec2i resolution) {
        this.resolution = resolution;
        this.updateVisibleRectangle(position);
    }

    public Vec2 getPosition() {
        return visionRectangle.topLeft();
    }

    public void setPosition(Vec2 position) {
        this.updateVisibleRectangle(position);
    }

    public Vec2i getResolution() {
        return resolution;
    }

    public void setResolution(Vec2i resolution) {
        this.resolution = Vectors.immutableVec2i(resolution);
        this.updateVisibleRectangle(getPosition());
    }

    public void move(Vec2 diff) {
        Vec2 newPosition = getPosition().plus(diff);
        setPosition(newPosition);
    }

    public Rect getVisionRectangle() {
        return this.visionRectangle;
    }

    private void updateVisibleRectangle(Vec2 newPosition) {
        this.visionRectangle = rect(newPosition, newPosition.plus(resolution));
    }
}
