package dev.peytob.rpg.client.event.handler.window.key;

import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Low-level event bus for handling raw GLFW key events.
 */
@Component
public final class WindowKeyEventBus implements GLFWKeyCallbackI {

    private final List<WindowKeyEventHandler> handlers;

    public WindowKeyEventBus(List<WindowKeyEventHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        handlers.forEach(handler -> handler.invoke(window, key, scancode, action, mods));
    }
}
