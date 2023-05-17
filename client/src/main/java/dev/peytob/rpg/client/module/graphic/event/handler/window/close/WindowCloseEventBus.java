package dev.peytob.rpg.client.module.graphic.event.handler.window.close;

import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class WindowCloseEventBus implements GLFWWindowCloseCallbackI {

    private final List<MainWindowCloseEventHandler> handlers;

    public WindowCloseEventBus(List<MainWindowCloseEventHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void invoke(long window) {
        handlers.forEach(handler -> handler.invoke(window));
    }
}
