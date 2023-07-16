package dev.peytob.rpg.client.module.graphic.context.system.rendering;

import dev.peytob.rpg.client.fsm.annotation.IncludeInAllStates;
import dev.peytob.rpg.client.module.graphic.context.component.CameraComponent;
import dev.peytob.rpg.client.module.graphic.model.Camera;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import dev.peytob.rpg.math.matrix.Mat4;
import dev.peytob.rpg.math.matrix.Matrices;
import dev.peytob.rpg.math.vector.Vec2;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static dev.peytob.rpg.client.module.graphic.model.RenderSystemDefaultOrder.MAIN_RENDERING;

@Component
@IncludeInAllStates(order = MAIN_RENDERING - 1)
public class CameraUpdatingSystem implements System {

    @Override
    public void execute(EcsContext context) {
        Optional<CameraComponent> camera = context.getSingletonComponentByType(CameraComponent.class);

        camera.ifPresent(component -> {
            Mat4 orthoMatrix = computeOrthoMatrix(component.getCamera());
            component.setProjectionMatrix(orthoMatrix);
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
