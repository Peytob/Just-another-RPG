package dev.peytob.rpg.client.graphic.model;

import dev.peytob.rpg.math.geometry.Rect;
import dev.peytob.rpg.math.vector.Vec2;

public class Camera {

    private Rect cameraRect;

    /**
     * Returns top-left corner of the camera rect
     */
    public Vec2 getPosition() {
        return cameraRect.topLeft();
    }

    /**
     * Returns center of the camera rect
     */
    public Vec2 getCenter() {
        return cameraRect.topLeft().plus(cameraRect.size().division(2f, 2f));
    }

    public Vec2 getResolution() {
        return cameraRect.size();
    }

    public Rect getRect() {
        return cameraRect;
    }
}
