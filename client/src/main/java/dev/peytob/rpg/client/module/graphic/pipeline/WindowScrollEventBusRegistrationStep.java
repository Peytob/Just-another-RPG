package dev.peytob.rpg.client.module.graphic.pipeline;

import dev.peytob.rpg.client.module.control.context.event.MouseScrollEvent;
import dev.peytob.rpg.client.module.graphic.model.Window;
import dev.peytob.rpg.engine.event.EngineEventBus;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import org.lwjgl.glfw.GLFW;
import org.springframework.stereotype.Component;

@Component
public final class WindowScrollEventBusRegistrationStep implements InitializingPipelineStep {

    private final Window window;

    private final EngineEventBus engineEventBus;

    public WindowScrollEventBusRegistrationStep(Window window, EngineEventBus engineEventBus) {
        this.window = window;
        this.engineEventBus = engineEventBus;
    }

    @Override
    public void execute() {
        GLFW.glfwSetScrollCallback(window.getPointer(), (window, xOffset, yOffset) -> {
            MouseScrollEvent event = createEvent(xOffset, yOffset);
            engineEventBus.publishEvent(event);
        });
    }

    private MouseScrollEvent createEvent(double xOffset, double yOffset) {
        return new MouseScrollEvent(xOffset, yOffset);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
