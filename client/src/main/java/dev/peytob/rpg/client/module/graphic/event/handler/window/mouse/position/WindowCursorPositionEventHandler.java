package dev.peytob.rpg.client.module.graphic.event.handler.window.mouse.position;

import org.lwjgl.glfw.GLFWCursorPosCallbackI;

/**
 * Adepter between GLFW and low-level engine event buses.
 */
public abstract class WindowCursorPositionEventHandler implements GLFWCursorPosCallbackI {

    @Override
    public final void invoke(long window, double xpos, double ypos) {
        handle(xpos, ypos);
    }

    public abstract void handle(double newX, double newY);
}
