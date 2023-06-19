package dev.peytob.rpg.client.module.graphic.state.event;

import dev.peytob.rpg.client.module.graphic.context.system.rendering.CameraUniformsUpdateSystem;
import dev.peytob.rpg.client.module.graphic.context.system.rendering.CameraUpdatingSystem;
import dev.peytob.rpg.client.module.graphic.context.system.rendering.FramebufferClearSystem;
import dev.peytob.rpg.client.module.graphic.context.system.utils.ScreenshotRenderingSystem;
import dev.peytob.rpg.client.module.graphic.context.system.window.WindowSwappingBuffersSystem;
import dev.peytob.rpg.client.state.InGameEngineState;
import dev.peytob.rpg.ecs.context.EcsContextBuilder;
import dev.peytob.rpg.engine.context.system.SystemFactory;
import dev.peytob.rpg.engine.state.event.StateSetUpEventHandler;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.client.module.graphic.model.RenderSystemDefaultOrder.*;

@Component
public class CreateBaseGraphicSystemsStateUpHandler extends StateSetUpEventHandler<InGameEngineState> {

    private final SystemFactory systemFactory;

    public CreateBaseGraphicSystemsStateUpHandler(SystemFactory systemFactory) {
        this.systemFactory = systemFactory;
    }

    @Override
    public void onStateSetUp(InGameEngineState engineState, EcsContextBuilder contextBuilder) {
        contextBuilder
            .addSystem(systemFactory.getSystem(FramebufferClearSystem.class), BEFORE_MAIN_RENDERING)
            .addSystem(systemFactory.getSystem(CameraUpdatingSystem.class), MAIN_RENDERING - 1)
            .addSystem(systemFactory.getSystem(CameraUniformsUpdateSystem.class), MAIN_RENDERING)
            .addSystem(systemFactory.getSystem(ScreenshotRenderingSystem.class), AFTER_MAIN_RENDERING - 1)
            .addSystem(systemFactory.getSystem(WindowSwappingBuffersSystem.class), AFTER_MAIN_RENDERING);
    }
}
