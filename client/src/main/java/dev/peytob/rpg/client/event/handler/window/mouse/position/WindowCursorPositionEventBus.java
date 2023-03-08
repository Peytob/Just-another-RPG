package dev.peytob.rpg.client.event.handler.window.mouse.position;

import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Low-level event bus for handling raw GLFW cursor events.
 */
@Component
public final class WindowCursorPositionEventBus implements GLFWCursorPosCallbackI {

    private final List<WindowCursorPositionEventHandler> handlers;

    public WindowCursorPositionEventBus(List<WindowCursorPositionEventHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void invoke(long window, double xpos, double ypos) {
        handlers.forEach(handler -> handler.invoke(window, xpos, ypos));
    }
}
