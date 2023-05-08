package dev.peytob.rpg.client.module.graphic.pipeline;

import dev.peytob.rpg.client.module.graphic.model.Window;
import dev.peytob.rpg.client.module.graphic.event.handler.window.key.WindowKeyEventBus;
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
