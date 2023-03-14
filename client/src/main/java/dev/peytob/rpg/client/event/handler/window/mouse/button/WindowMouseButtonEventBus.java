package dev.peytob.rpg.client.event.handler.window.mouse.button;

import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Low-level event bus for handling raw GLFW mouse buttons events.
 */
@Component
public final class WindowMouseButtonEventBus implements GLFWMouseButtonCallbackI {

    private final List<WindowMouseButtonEventHandler> handlers;

    public WindowMouseButtonEventBus(List<WindowMouseButtonEventHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void invoke(long window, int button, int action, int mods) {
        handlers.forEach(handler -> handler.invoke(window, button, action, mods));
    }
}
