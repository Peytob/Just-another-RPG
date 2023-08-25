package dev.peytob.rpg.client.module.control.context.system;

import dev.peytob.rpg.client.fsm.annotation.IncludeInState;
import dev.peytob.rpg.client.fsm.state.instance.InGameEngineState;
import dev.peytob.rpg.client.module.graphic.context.component.CameraComponent;
import dev.peytob.rpg.client.module.graphic.model.Window;
import dev.peytob.rpg.client.module.network.service.PlayerMovingService;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import dev.peytob.rpg.math.vector.Vec2;
import dev.peytob.rpg.math.vector.Vectors;
import org.lwjgl.glfw.GLFW;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static dev.peytob.rpg.ecs.model.SystemDefaultOrder.INPUT_HANDLING;
import static dev.peytob.rpg.math.vector.Vectors.immutableVec2;

// Test implementation!

@Component
@IncludeInState(state = InGameEngineState.class, order = INPUT_HANDLING)
public final class PlayerMovingSystem implements System {

    private final static Integer FORWARD = GLFW.GLFW_KEY_W;
    private final static Integer BACK = GLFW.GLFW_KEY_S;
    private final static Integer LEFT = GLFW.GLFW_KEY_A;
    private final static Integer RIGHT = GLFW.GLFW_KEY_D;

    private final Window window;

    private final PlayerMovingService playerMovingService;

    public PlayerMovingSystem(Window window, PlayerMovingService playerMovingService) {
        this.window = window;
        this.playerMovingService = playerMovingService;
    }

    // Temporary just controls camera position
    @Override
    public void execute(EcsContext context) {
        Optional<CameraComponent> optionalCamera = context.getSingletonComponentByType(CameraComponent.class);

        if (optionalCamera.isEmpty()) {
            return;
        }

        CameraComponent camera = optionalCamera.get();

        Vec2 direction = immutableVec2();
        float speed = 3f;

        if (GLFW.glfwGetKey(window.getPointer(), FORWARD) == GLFW.GLFW_PRESS) {
            direction = direction.plus(0.0f, -1);
        }

        if (GLFW.glfwGetKey(window.getPointer(), BACK) == GLFW.GLFW_PRESS) {
            direction = direction.plus(0.0f, 1);
        }

        if (GLFW.glfwGetKey(window.getPointer(), LEFT) == GLFW.GLFW_PRESS) {
            direction = direction.plus(-1, 0.0f);
        }

        if (GLFW.glfwGetKey(window.getPointer(), RIGHT) == GLFW.GLFW_PRESS) {
            direction = direction.plus(1, 0.0f);
        }

        Vec2 normalizedDirection = Vectors.normalize(direction);

        if (!normalizedDirection.equals(immutableVec2())) {
            Vec2 newCameraPosition = camera.getCamera().getPosition().plus(normalizedDirection.multiply(speed));
            camera.getCamera().setPosition(newCameraPosition);
            playerMovingService.directionalMove(normalizedDirection);
        }
    }
}
