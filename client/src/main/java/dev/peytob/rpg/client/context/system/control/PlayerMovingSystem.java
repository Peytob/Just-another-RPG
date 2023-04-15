package dev.peytob.rpg.client.context.system.control;

import dev.peytob.rpg.client.context.component.basic.PositionComponent;
import dev.peytob.rpg.client.model.graphic.Window;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.ecs.system.System;
import dev.peytob.rpg.math.vector.Vec2;
import dev.peytob.rpg.math.vector.Vectors;
import org.lwjgl.glfw.GLFW;
import org.springframework.stereotype.Component;

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

    @Override
    public void execute(EcsContext context) {
        Entity player = context.getUnmodifiableEntityManager().getById("player");

        PositionComponent positionComponent = player.getComponent(PositionComponent.class);

        Vec2 currentPosition = positionComponent.getPosition();
        float speed = 0.01f;

        if (GLFW.glfwGetKey(window.getPointer(), FORWARD) == GLFW.GLFW_PRESS) {
            currentPosition = currentPosition.plus(Vectors.immutableVec2(0.0f, speed));
        }

        if (GLFW.glfwGetKey(window.getPointer(),BACK) == GLFW.GLFW_PRESS) {
            currentPosition = currentPosition.plus(Vectors.immutableVec2(0.0f, -speed));
        }

        if (GLFW.glfwGetKey(window.getPointer(),LEFT) == GLFW.GLFW_PRESS) {
            currentPosition = currentPosition.plus(Vectors.immutableVec2(-speed, 0.0f));
        }

        if (GLFW.glfwGetKey(window.getPointer(),RIGHT) == GLFW.GLFW_PRESS) {
            currentPosition = currentPosition.plus(Vectors.immutableVec2(speed, 0.0f));
        }

        positionComponent.setPosition(currentPosition);
    }
}
