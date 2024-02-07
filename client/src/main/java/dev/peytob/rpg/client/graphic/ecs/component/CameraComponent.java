package dev.peytob.rpg.client.graphic.ecs.component;

import dev.peytob.rpg.client.graphic.model.Camera;
import dev.peytob.rpg.ecs.component.SingletonComponent;

public class CameraComponent implements SingletonComponent {

    private final Camera camera;

    public CameraComponent(Camera camera) {
        this.camera = camera;
    }

    public Camera getCamera() {
        return camera;
    }
}
