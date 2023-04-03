package dev.peytob.rpg.client.model.graphic;

import dev.peytob.rpg.math.vector.Vec2;
import dev.peytob.rpg.math.vector.Vec2i;

public final class Camera {

    private Vec2 position;

    private Vec2i resolution;

    public Camera(Vec2 position, Vec2i resolution) {
        this.position = position;
        this.resolution = resolution;
    }

    public Vec2 getPosition() {
        return position;
    }

    public void setPosition(Vec2 position) {
        this.position = position;
    }

    public Vec2i getResolution() {
        return resolution;
    }

    public void setResolution(Vec2i resolution) {
        this.resolution = resolution;
    }

    public void move(Vec2 diff) {
        this.position.plus(diff);
    }
}
