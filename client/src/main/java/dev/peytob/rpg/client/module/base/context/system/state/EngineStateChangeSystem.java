package dev.peytob.rpg.client.module.base.context.system.state;

import dev.peytob.rpg.client.module.base.context.event.ChangeStateEcsEvent;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import dev.peytob.rpg.engine.event.EngineEventBus;
import dev.peytob.rpg.engine.state.event.instance.ChangeStateEvent;
import org.springframework.stereotype.Component;

@Component
public class EngineStateChangeSystem implements System {

    private final EngineEventBus engineEventBus;

    public EngineStateChangeSystem(EngineEventBus engineEventBus) {
        this.engineEventBus = engineEventBus;
    }

    @Override
    public void execute(EcsContext context) {
        context.getEventsByType(ChangeStateEcsEvent.class).stream()
            .findFirst()
            .ifPresent(event -> engineEventBus.publishEvent(new ChangeStateEvent(event.getEngineState())));
    }
}
