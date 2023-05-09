package dev.peytob.rpg.client.module.graphic.event.handler.window.key;

import dev.peytob.rpg.client.module.control.model.KeyAction;
import dev.peytob.rpg.client.module.control.model.KeyModifiers;
import dev.peytob.rpg.client.module.control.model.KeyboardKey;
import org.lwjgl.glfw.GLFWKeyCallbackI;

/**
 * Adapter between GLFW and low-level engine event buses.
 */
public abstract class WindowKeyEventHandler implements GLFWKeyCallbackI {

    @Override
    public final void invoke(long window, int key, int scancode, int action, int mods) {
        // TODO Add memorization for all GLFW keys and possible modifiers sets
        handle(new KeyboardKey(scancode, key), KeyAction.fromGlfwAction(action), new KeyModifiers(mods));
    }

    public abstract void handle(KeyboardKey key, KeyAction action, KeyModifiers modifiers);
}
