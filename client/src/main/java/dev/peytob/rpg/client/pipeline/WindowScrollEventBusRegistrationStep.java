package dev.peytob.rpg.client.pipeline;

import dev.peytob.rpg.client.event.handler.window.mouse.scroll.WindowScrollEventBus;
import dev.peytob.rpg.client.model.graphics.Window;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import org.lwjgl.glfw.GLFW;
import org.springframework.stereotype.Component;

@Component
public final class WindowScrollEventBusRegistrationStep implements InitializingPipelineStep {

    private final Window window;

    private final WindowScrollEventBus windowScrollEventBus;

    public WindowScrollEventBusRegistrationStep(Window window, WindowScrollEventBus windowScrollEventBus) {
        this.window = window;
        this.windowScrollEventBus = windowScrollEventBus;
    }

    @Override
    public void execute() {
        GLFW.glfwSetScrollCallback(window.getPointer(), windowScrollEventBus);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
