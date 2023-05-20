package dev.peytob.rpg.client.module.graphic.context.component;

import dev.peytob.rpg.client.module.graphic.model.Camera;
import dev.peytob.rpg.ecs.component.SingletonComponent;
import dev.peytob.rpg.math.matrix.Mat4;

public final class CameraComponent implements SingletonComponent {

    private final Camera camera;
    private Mat4 projectionMatrix;

    public CameraComponent(Camera camera) {
        this.camera = camera;
    }

    public Camera getCamera() {
        return camera;
    }

    public Mat4 getProjectionMatrix() {
        return projectionMatrix;
    }

    public void setProjectionMatrix(Mat4 projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }
}
