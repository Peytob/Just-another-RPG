package dev.peytob.rpg.client.module.control.context.system;

import dev.peytob.rpg.client.module.graphic.context.component.CameraComponent;
import dev.peytob.rpg.client.module.graphic.model.Window;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import dev.peytob.rpg.math.vector.Vec2;
import dev.peytob.rpg.math.vector.Vectors;
import org.lwjgl.glfw.GLFW;
import org.springframework.stereotype.Component;

import java.util.Optional;

// Test implementation!

@Component
public final class PlayerMovingSystem implements System {

    private final static Integer FORWARD = GLFW.GLFW_KEY_W;
    private final static Integer BACK = GLFW.GLFW_KEY_S;
    private final static Integer LEFT = GLFW.GLFW_KEY_A;
    private final static Integer RIGHT = GLFW.GLFW_KEY_D;

    private final Window window;

    public PlayerMovingSystem(Window window) {
        this.window = window;
    }

    // Temporary just controls camera position
    @Override
    public void execute(EcsContext context) {
        Optional<CameraComponent> optionalCamera = context.getSingletonComponentByType(CameraComponent.class);

        if (optionalCamera.isEmpty()) {
            return;
        }

        CameraComponent camera = optionalCamera.get();

        Vec2 currentPosition = camera.getCamera().getPosition();
        float speed = 3f;

        if (GLFW.glfwGetKey(window.getPointer(), FORWARD) == GLFW.GLFW_PRESS) {
            currentPosition = currentPosition.plus(Vectors.immutableVec2(0.0f, -speed));
        }

        if (GLFW.glfwGetKey(window.getPointer(), BACK) == GLFW.GLFW_PRESS) {
            currentPosition = currentPosition.plus(Vectors.immutableVec2(0.0f, speed));
        }

        if (GLFW.glfwGetKey(window.getPointer(), LEFT) == GLFW.GLFW_PRESS) {
            currentPosition = currentPosition.plus(Vectors.immutableVec2(-speed, 0.0f));
        }

        if (GLFW.glfwGetKey(window.getPointer(), RIGHT) == GLFW.GLFW_PRESS) {
            currentPosition = currentPosition.plus(Vectors.immutableVec2(speed, 0.0f));
        }

        camera.getCamera().setPosition(currentPosition);
    }
}
