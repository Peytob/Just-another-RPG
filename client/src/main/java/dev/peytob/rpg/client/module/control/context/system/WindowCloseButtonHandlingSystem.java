package dev.peytob.rpg.client.module.control.context.system;

import dev.peytob.rpg.client.fsm.annotation.IncludeInState;
import dev.peytob.rpg.client.fsm.state.instance.InGameEngineState;
import dev.peytob.rpg.client.module.control.context.event.KeyboardKeyEvent;
import dev.peytob.rpg.client.module.graphic.context.event.window.WindowCloseEvent;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import org.lwjgl.glfw.GLFW;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.ecs.model.SystemDefaultOrder.INPUT_HANDLING;

@Component
@IncludeInState(state = InGameEngineState.class, order = INPUT_HANDLING)
public class WindowCloseButtonHandlingSystem implements System {

    private static final Integer EXIT = GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_ESCAPE);

    @Override
    public void execute(EcsContext context) {
        context.getEventsByType(KeyboardKeyEvent.class).stream()
            // TODO Make an control settings class
            .filter(event -> event.key().scancode().equals(EXIT))
            .findFirst()
            .ifPresent(event -> context.addEvent(new WindowCloseEvent()));
    }
}
