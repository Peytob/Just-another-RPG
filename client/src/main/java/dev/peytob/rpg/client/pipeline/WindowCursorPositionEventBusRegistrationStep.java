package dev.peytob.rpg.client.pipeline;

import dev.peytob.rpg.client.event.handler.window.mouse.position.WindowCursorPositionEventHandler;
import dev.peytob.rpg.client.model.graphic.Window;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import org.lwjgl.glfw.GLFW;
import org.springframework.stereotype.Component;

@Component
public final class WindowCursorPositionEventBusRegistrationStep implements InitializingPipelineStep {

    private final Window window;

    private final WindowCursorPositionEventHandler windowCursorPositionEventHandler;

    public WindowCursorPositionEventBusRegistrationStep(Window window, WindowCursorPositionEventHandler windowCursorPositionEventHandler) {
        this.window = window;
        this.windowCursorPositionEventHandler = windowCursorPositionEventHandler;
    }

    @Override
    public void execute() {
        GLFW.glfwSetCursorPosCallback(window.getPointer(), windowCursorPositionEventHandler);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
