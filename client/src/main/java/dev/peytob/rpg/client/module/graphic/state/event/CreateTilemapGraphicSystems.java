package dev.peytob.rpg.client.module.graphic.state.event;

import dev.peytob.rpg.client.module.graphic.context.system.rendering.TilemapRenderSystem;
import dev.peytob.rpg.client.state.InGameEngineState;
import dev.peytob.rpg.ecs.context.EcsContextBuilder;
import dev.peytob.rpg.engine.context.system.SystemFactory;
import dev.peytob.rpg.engine.state.event.StateSetUpEventHandler;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.client.module.graphic.model.RenderSystemDefaultOrder.MAIN_RENDERING;

@Component
public class CreateTilemapGraphicSystems extends StateSetUpEventHandler<InGameEngineState> {

    private final SystemFactory systemFactory;

    public CreateTilemapGraphicSystems(SystemFactory systemFactory) {
        this.systemFactory = systemFactory;
    }

    @Override
    public void onStateSetUp(InGameEngineState engineState, EcsContextBuilder contextBuilder) {
        contextBuilder
            .addSystem(systemFactory.getSystem(TilemapRenderSystem.class), MAIN_RENDERING);
    }
}
