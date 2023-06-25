package dev.peytob.rpg.client.module.graphic.pipeline;

import dev.peytob.rpg.client.module.control.context.event.MouseButtonEvent;
import dev.peytob.rpg.client.module.control.model.KeyAction;
import dev.peytob.rpg.client.module.control.model.KeyModifiers;
import dev.peytob.rpg.client.module.control.model.MouseButton;
import dev.peytob.rpg.client.module.graphic.model.Window;
import dev.peytob.rpg.engine.event.EngineEventBus;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import org.lwjgl.glfw.GLFW;
import org.springframework.stereotype.Component;

@Component
public final class WindowMouseButtonEventBusRegistrationStep implements InitializingPipelineStep {

    private final Window window;

    private final EngineEventBus engineEventBus;

    public WindowMouseButtonEventBusRegistrationStep(Window window, EngineEventBus engineEventBus) {
        this.window = window;
        this.engineEventBus = engineEventBus;
    }

    @Override
    public void execute() {
        GLFW.glfwSetMouseButtonCallback(window.getPointer(), (window, button, action, mods) -> {
            MouseButtonEvent event = createEvent(button, action, mods);
            engineEventBus.publishEvent(event);
        });
    }

    private MouseButtonEvent createEvent(int button, int action, int mods) {
        // TODO Add memorization for all GLFW mouse buttons and possible modifiers sets
        return new MouseButtonEvent(new MouseButton(button), KeyAction.fromGlfwAction(action), new KeyModifiers(mods));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
