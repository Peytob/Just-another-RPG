package dev.peytob.rpg.client.module.graphic.model;

import dev.peytob.rpg.math.vector.Vec2;
import dev.peytob.rpg.math.vector.Vec2i;
import dev.peytob.rpg.math.vector.Vectors;

public final class Camera {

    private Vec2 position;

    private Vec2i resolution;

    public Camera(Vec2 position, Vec2i resolution) {
        this.setPosition(position);
        this.setResolution(resolution);
    }

    public Vec2 getPosition() {
        return position;
    }

    public void setPosition(Vec2 position) {
        this.position = Vectors.immutableVec2(position);
    }

    public Vec2i getResolution() {
        return resolution;
    }

    public void setResolution(Vec2i resolution) {
        this.resolution = Vectors.immutableVec2i(resolution);
    }

    public void move(Vec2 diff) {
        this.position.plus(diff);
    }
}
