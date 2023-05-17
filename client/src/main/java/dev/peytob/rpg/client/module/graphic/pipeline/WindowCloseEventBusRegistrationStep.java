package dev.peytob.rpg.client.module.graphic.pipeline;

import dev.peytob.rpg.client.module.graphic.event.handler.window.close.WindowCloseEventBus;
import dev.peytob.rpg.client.module.graphic.model.Window;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import org.lwjgl.glfw.GLFW;
import org.springframework.stereotype.Component;

@Component
public final class WindowCloseEventBusRegistrationStep implements InitializingPipelineStep {

    private final Window window;

    private final WindowCloseEventBus windowCloseEventBus;

    public WindowCloseEventBusRegistrationStep(Window window, WindowCloseEventBus windowCloseEventBus) {
        this.window = window;
        this.windowCloseEventBus = windowCloseEventBus;
    }

    @Override
    public void execute() {
        GLFW.glfwSetWindowCloseCallback(window.getPointer(), windowCloseEventBus);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
