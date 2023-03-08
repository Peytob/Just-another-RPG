package dev.peytob.rpg.client.pipeline;

import dev.peytob.rpg.client.model.graphics.Window;
import dev.peytob.rpg.client.event.handler.window.key.WindowKeyEventBus;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import org.lwjgl.glfw.GLFW;
import org.springframework.stereotype.Component;

@Component
public final class WindowKeyEventBusRegistrationStep implements InitializingPipelineStep {

    private final Window window;

    private final WindowKeyEventBus windowKeyEventBus;

    public WindowKeyEventBusRegistrationStep(Window window, WindowKeyEventBus windowKeyEventBus) {
        this.window = window;
        this.windowKeyEventBus = windowKeyEventBus;
    }

    @Override
    public void execute() {
        GLFW.glfwSetKeyCallback(window.getPointer(), windowKeyEventBus);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
