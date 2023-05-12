package dev.peytob.rpg.client.module.graphic.event.handler.window.mouse.scroll;

import org.lwjgl.glfw.GLFWScrollCallbackI;

/**
 * Adepter between GLFW and low-level engine event buses.
 */
public abstract class WindowScrollEventHandler implements GLFWScrollCallbackI {

    @Override
    public final void invoke(long window, double xoffset, double yoffset) {
        handle(xoffset, yoffset);
    }

    public abstract void handle(double xOffset, double yOffset);
}
