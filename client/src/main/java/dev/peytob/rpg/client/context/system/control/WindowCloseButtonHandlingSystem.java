package dev.peytob.rpg.client.context.system.control;

import dev.peytob.rpg.client.context.event.input.KeyboardKeyEvent;
import dev.peytob.rpg.client.context.event.window.WindowCloseEvent;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.event.EventManager;
import dev.peytob.rpg.ecs.system.System;
import org.lwjgl.glfw.GLFW;
import org.springframework.stereotype.Component;

@Component
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
