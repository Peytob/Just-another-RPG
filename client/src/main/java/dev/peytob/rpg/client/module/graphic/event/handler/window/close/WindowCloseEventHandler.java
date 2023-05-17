package dev.peytob.rpg.client.module.graphic.event.handler.window.close;

import org.lwjgl.glfw.GLFWWindowCloseCallbackI;

public abstract class WindowCloseEventHandler implements GLFWWindowCloseCallbackI {

    @Override
    public final void invoke(long window) {
        handle();
    }

    public abstract void handle();
}
