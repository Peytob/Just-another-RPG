package dev.peytob.rpg.client.module.graphic.pipeline;

import dev.peytob.rpg.client.module.control.context.event.KeyboardKeyEvent;
import dev.peytob.rpg.client.module.control.model.KeyAction;
import dev.peytob.rpg.client.module.control.model.KeyModifiers;
import dev.peytob.rpg.client.module.control.model.KeyboardKey;
import dev.peytob.rpg.client.module.graphic.model.Window;
import dev.peytob.rpg.engine.event.MainEventBus;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import org.lwjgl.glfw.GLFW;
import org.springframework.stereotype.Component;

@Component
public final class WindowKeyEventBusRegistrationStep implements InitializingPipelineStep {

    private final Window window;

    private final MainEventBus mainEventBus;

    public WindowKeyEventBusRegistrationStep(Window window, MainEventBus mainEventBus) {
        this.window = window;
        this.mainEventBus = mainEventBus;
    }

    @Override
    public void execute() {
        GLFW.glfwSetKeyCallback(window.getPointer(), (long window, int key, int scancode, int action, int mods) -> {
            KeyboardKeyEvent event = createEvent(key, scancode, action, mods);
            mainEventBus.addEvent(event);
        });
    }

    private KeyboardKeyEvent createEvent(int key, int scancode, int action, int mods) {
        // TODO Add memorization for all GLFW keys and possible modifiers sets
        return new KeyboardKeyEvent(new KeyboardKey(scancode, key), KeyAction.fromGlfwAction(action), new KeyModifiers(mods));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
