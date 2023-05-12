package dev.peytob.rpg.client.module.graphic.event.handler.window.mouse.scroll;

import org.lwjgl.glfw.GLFWScrollCallbackI;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Low-level event bus for handling raw GLFW scroll events.
 */
@Component
public final class WindowScrollEventBus implements GLFWScrollCallbackI {

    private final List<WindowScrollEventHandler> handlers;

    public WindowScrollEventBus(List<WindowScrollEventHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void invoke(long window, double xOffset, double yOffset) {
        handlers.forEach(handler -> handler.invoke(window, xOffset, yOffset));
    }
}
