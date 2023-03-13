package dev.peytob.rpg.client.event.handler.window.mouse.button;

import dev.peytob.rpg.client.model.control.KeyAction;
import dev.peytob.rpg.client.model.control.KeyModifiers;
import dev.peytob.rpg.client.model.control.MouseButton;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

/**
 * Adepter between GLFW and low-level engine event buses.
 */
public abstract class WindowMouseButtonEventHandler implements GLFWMouseButtonCallbackI {

    @Override
    public final void invoke(long window, int button, int action, int mods) {
        // TODO Add memorization for all GLFW mouse buttons and possible modifiers sets
        handle(new MouseButton(button), KeyAction.fromGlfwAction(action), new KeyModifiers(mods));
    }

    public abstract void handle(MouseButton button, KeyAction action, KeyModifiers modifiers);
}
