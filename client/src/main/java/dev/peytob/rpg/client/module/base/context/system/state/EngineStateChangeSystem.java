package dev.peytob.rpg.client.module.base.context.system.state;

import dev.peytob.rpg.client.fsm.annotation.IncludeInAllStates;
import dev.peytob.rpg.client.module.base.context.event.ChangeStateEcsEvent;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import dev.peytob.rpg.engine.event.EngineEventBus;
import dev.peytob.rpg.client.fsm.event.instance.ChangeStateEvent;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.ecs.model.SystemDefaultOrder.AFTER_ALL;

@Component
@IncludeInAllStates(order = AFTER_ALL)
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
