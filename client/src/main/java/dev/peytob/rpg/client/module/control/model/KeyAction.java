package dev.peytob.rpg.client.module.control.model;

import static org.lwjgl.glfw.GLFW.*;

public enum KeyAction {
    UNKNOWN,
    PRESS,
    REPEAT,
    RELEASE;

    public static KeyAction fromGlfwAction(int action) {
        return switch (action) {
            case GLFW_PRESS -> PRESS;
            case GLFW_REPEAT -> REPEAT;
            case GLFW_RELEASE -> RELEASE;
            default -> UNKNOWN;
        };
    }
}
