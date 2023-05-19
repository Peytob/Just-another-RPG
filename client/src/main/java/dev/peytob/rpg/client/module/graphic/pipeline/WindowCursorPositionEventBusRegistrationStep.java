package dev.peytob.rpg.client.module.graphic.pipeline;

import dev.peytob.rpg.client.module.control.context.event.CursorPositionEvent;
import dev.peytob.rpg.client.module.graphic.model.Window;
import dev.peytob.rpg.engine.event.MainEventBus;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import org.lwjgl.glfw.GLFW;
import org.springframework.stereotype.Component;

@Component
public final class WindowCursorPositionEventBusRegistrationStep implements InitializingPipelineStep {

    private final Window window;

    private final MainEventBus mainEventBus;

    public WindowCursorPositionEventBusRegistrationStep(Window window, MainEventBus mainEventBus) {
        this.window = window;
        this.mainEventBus = mainEventBus;
    }

    @Override
    public void execute() {
        GLFW.glfwSetCursorPosCallback(window.getPointer(), ((window, xpos, ypos) -> {
            CursorPositionEvent event = createEvent(xpos, ypos);
            mainEventBus.addEvent(event);
        }));
    }

    private CursorPositionEvent createEvent(double newX, double newY) {
        return new CursorPositionEvent(
            newX,
            newY,
            newX - window.getWidth() / 2.0,
            newY - window.getHeight() / 2.0);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
