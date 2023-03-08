package dev.peytob.rpg.client.pipeline;

import dev.peytob.rpg.client.event.handler.window.mouse.key.scroll.WindowMouseButtonEventBus;
import dev.peytob.rpg.client.model.graphics.Window;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import org.lwjgl.glfw.GLFW;
import org.springframework.stereotype.Component;

@Component
public final class WindowMouseButtonEventBusRegistrationStep implements InitializingPipelineStep {

    private final Window window;

    private final WindowMouseButtonEventBus windowMouseButtonEventBus;

    public WindowMouseButtonEventBusRegistrationStep(Window window, WindowMouseButtonEventBus windowMouseButtonEventBus) {
        this.window = window;
        this.windowMouseButtonEventBus = windowMouseButtonEventBus;
    }

    @Override
    public void execute() {
        GLFW.glfwSetMouseButtonCallback(window.getPointer(), windowMouseButtonEventBus);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
