package dev.peytob.rpg.client.module.graphic.pipeline;

import dev.peytob.rpg.client.module.graphic.context.event.window.WindowCloseEvent;
import dev.peytob.rpg.client.module.graphic.model.Window;
import dev.peytob.rpg.engine.event.EngineEventBus;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import org.lwjgl.glfw.GLFW;
import org.springframework.stereotype.Component;

@Component
public final class WindowCloseEventBusRegistrationStep implements InitializingPipelineStep {

    private final Window window;

    private final EngineEventBus engineEventBus;

    public WindowCloseEventBusRegistrationStep(Window window, EngineEventBus engineEventBus) {
        this.window = window;
        this.engineEventBus = engineEventBus;
    }

    @Override
    public void execute() {
        GLFW.glfwSetWindowCloseCallback(window.getPointer(), (windowPointer) ->
            engineEventBus.publishEvent(new WindowCloseEvent()));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
