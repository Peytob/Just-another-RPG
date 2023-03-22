package dev.peytob.rpg.client.pipeline;

import dev.peytob.rpg.client.model.graphic.Window;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import org.springframework.stereotype.Component;

@Component
public final class WindowShowInitializingStep implements InitializingPipelineStep {

    private final Window window;

    public WindowShowInitializingStep(Window window) {
        this.window = window;
    }

    @Override
    public void execute() {
        window.show();
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
