package dev.peytob.rpg.client.module.graphic.state.event;

import dev.peytob.rpg.client.module.graphic.context.system.rendering.TilemapRenderSystem;
import dev.peytob.rpg.client.state.InGameEngineState;
import dev.peytob.rpg.ecs.context.EcsContextBuilder;
import dev.peytob.rpg.engine.context.system.SystemFactory;
import dev.peytob.rpg.engine.state.event.instance.StateSetUpEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.client.module.graphic.model.RenderSystemDefaultOrder.MAIN_RENDERING;

@Component
public class CreateTilemapGraphicSystems {

    private final SystemFactory systemFactory;

    private final InGameEngineState inGameEngineState;

    public CreateTilemapGraphicSystems(SystemFactory systemFactory, InGameEngineState inGameEngineState) {
        this.systemFactory = systemFactory;
        this.inGameEngineState = inGameEngineState;
    }

    @EventListener
    public void onStateSetUp(StateSetUpEvent stateSetUpEvent) {
        if (stateSetUpEvent.engineState() != inGameEngineState) {
            return;
        }

        EcsContextBuilder contextBuilder = stateSetUpEvent.contextBuilder();

        contextBuilder
            .addSystem(systemFactory.getSystem(TilemapRenderSystem.class), MAIN_RENDERING);
    }
}
