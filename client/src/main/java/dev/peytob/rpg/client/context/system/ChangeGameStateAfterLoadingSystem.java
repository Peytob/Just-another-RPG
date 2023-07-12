package dev.peytob.rpg.client.context.system;

import dev.peytob.rpg.client.module.base.context.component.loading.TilemapAsyncLoadingComponent;
import dev.peytob.rpg.client.fsm.state.instance.InGameEngineState;
import dev.peytob.rpg.core.context.component.template.AsyncTaskComponent;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import dev.peytob.rpg.engine.event.EngineEventBus;
import dev.peytob.rpg.client.fsm.event.instance.ChangeStateEvent;
import org.springframework.stereotype.Component;

@Component
public class ChangeGameStateAfterLoadingSystem implements System {

    private final EngineEventBus engineEventBus;

    private final InGameEngineState inGameEngineState;

    public ChangeGameStateAfterLoadingSystem(EngineEventBus engineEventBus, InGameEngineState inGameEngineState) {
        this.engineEventBus = engineEventBus;
        this.inGameEngineState = inGameEngineState;
    }

    @Override
    public void execute(EcsContext context) {
        boolean isAllComponentsLoaded = context.getComponentsByType(TilemapAsyncLoadingComponent.class).stream()
            .allMatch(AsyncTaskComponent::isDone);

        if (isAllComponentsLoaded) {
            engineEventBus.publishEvent(new ChangeStateEvent(inGameEngineState));
        }
    }
}
