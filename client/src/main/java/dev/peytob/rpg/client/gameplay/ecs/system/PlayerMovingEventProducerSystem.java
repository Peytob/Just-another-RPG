package dev.peytob.rpg.client.gameplay.ecs.system;

import dev.peytob.rpg.client.fsm.annotation.IncludeInState;
import dev.peytob.rpg.client.fsm.state.InGameState;
import dev.peytob.rpg.client.gameplay.ecs.event.PlayerMovingEvent;
import dev.peytob.rpg.client.input.hid.ecs.event.KeyboardKeyEvent;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.model.SystemDefaultOrder;
import dev.peytob.rpg.ecs.system.System;
import dev.peytob.rpg.math.vector.Vec2;
import org.lwjgl.glfw.GLFW;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static dev.peytob.rpg.math.vector.Vectors.immutableVec2;

@Component
@IncludeInState(value = InGameState.class, order = SystemDefaultOrder.INPUT_HANDLING)
public class PlayerMovingEventProducerSystem implements System {

    @Override
    public void execute(EcsContext context) {
        final Vec2[] movingDirection = {immutableVec2()};
        float movingSpeed = 0.25f; // TODO Get from something component...

        context.getEventsByType(KeyboardKeyEvent.class).stream()
            .filter(keyboardKeyEvent -> keyboardKeyEvent.key().keyCode() == GLFW.GLFW_KEY_W)
            .findFirst()
            .ifPresent(keyboardKeyEvent -> movingDirection[0] = movingDirection[0].plus(0f, 1f));

        context.getEventsByType(KeyboardKeyEvent.class).stream()
            .filter(keyboardKeyEvent -> keyboardKeyEvent.key().keyCode() == GLFW.GLFW_KEY_S)
            .findFirst()
            .ifPresent(keyboardKeyEvent -> movingDirection[0] =movingDirection[0].plus(0f, -1f));

        context.getEventsByType(KeyboardKeyEvent.class).stream()
            .filter(keyboardKeyEvent -> keyboardKeyEvent.key().keyCode() == GLFW.GLFW_KEY_A)
            .findFirst()
            .ifPresent(keyboardKeyEvent -> movingDirection[0] =movingDirection[0].plus(-1f, 0f));

        context.getEventsByType(KeyboardKeyEvent.class).stream()
            .filter(keyboardKeyEvent -> keyboardKeyEvent.key().keyCode() == GLFW.GLFW_KEY_D)
            .findFirst()
            .ifPresent(keyboardKeyEvent -> movingDirection[0] = movingDirection[0].plus(1f, 0f));

        if (!Objects.equals(movingDirection[0], immutableVec2())) {
            context.addEvent(new PlayerMovingEvent(movingDirection[0], movingSpeed));
        }
    }
}
