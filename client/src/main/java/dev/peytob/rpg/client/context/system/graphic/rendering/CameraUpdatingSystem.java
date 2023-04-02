package dev.peytob.rpg.client.context.system.graphic.rendering;

import dev.peytob.rpg.client.context.component.graphic.CameraComponent;
import dev.peytob.rpg.client.model.graphic.Camera;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import dev.peytob.rpg.math.matrix.Mat4;
import dev.peytob.rpg.math.matrix.Matrices;
import dev.peytob.rpg.math.vector.Vec2;

public class CameraUpdatingSystem implements System {

    @Override
    public void execute(EcsContext context) {
        context
            .getUnmodifiableComponentManager()
            .getAllByType(CameraComponent.class)
            .forEach(cameraComponent -> {
                Camera camera = cameraComponent.getCamera();
                cameraComponent.setProjectionMatrix(computeOrthoMatrix(camera));
            });
    }

    private Mat4 computeOrthoMatrix(Camera camera) {
        Vec2 topLeftScreenPoint = camera.getPosition();
        Vec2 rightBottomScreenPoints = camera.getPosition().plus(camera.getResolution());

        Mat4 ortho = Matrices.ortho2D(
                topLeftScreenPoint.x(),
                rightBottomScreenPoints.x(),
                rightBottomScreenPoints.y(),
                topLeftScreenPoint.y());

        return Matrices.immutableMat4(ortho);
    }
}
