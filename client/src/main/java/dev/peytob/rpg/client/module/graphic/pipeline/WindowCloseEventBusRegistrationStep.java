package dev.peytob.rpg.client.module.graphic.pipeline;

import dev.peytob.rpg.client.module.graphic.context.event.window.WindowCloseEvent;
import dev.peytob.rpg.client.module.graphic.model.Window;
import dev.peytob.rpg.engine.event.EventBus;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import org.lwjgl.glfw.GLFW;
import org.springframework.stereotype.Component;

@Component
public final class WindowCloseEventBusRegistrationStep implements InitializingPipelineStep {

    private final Window window;

    private final EventBus eventBus;

    public WindowCloseEventBusRegistrationStep(Window window, EventBus eventBus) {
        this.window = window;
        this.eventBus = eventBus;
    }

    @Override
    public void execute() {
        GLFW.glfwSetWindowCloseCallback(window.getPointer(), (windowPointer) ->
            eventBus.addEvent(new WindowCloseEvent()));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
