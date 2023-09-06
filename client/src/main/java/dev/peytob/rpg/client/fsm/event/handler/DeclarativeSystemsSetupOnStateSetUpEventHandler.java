package dev.peytob.rpg.client.fsm.event.handler;

import dev.peytob.rpg.client.fsm.EngineState;
import dev.peytob.rpg.client.fsm.event.instance.BeforeEngineStateSetEvent;
import dev.peytob.rpg.client.fsm.service.StateSystemsManager;
import dev.peytob.rpg.ecs.context.EcsContextBuilder;
import dev.peytob.rpg.ecs.system.OrderedSystem;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class DeclarativeSystemsSetupOnStateSetUpEventHandler {

    private final StateSystemsManager stateSystemsManager;

    public DeclarativeSystemsSetupOnStateSetUpEventHandler(StateSystemsManager stateSystemsManager) {
        this.stateSystemsManager = stateSystemsManager;
    }

    @EventListener
    void setupDeclarativeSystems(BeforeEngineStateSetEvent stateSetUpEvent) {
        EcsContextBuilder ecsContextBuilder = stateSetUpEvent.contextBuilder();
        EngineState engineState = stateSetUpEvent.engineState();
        Collection<OrderedSystem> systemsForState = stateSystemsManager.getSystemsForState(engineState);
        systemsForState.forEach(ecsContextBuilder::addSystem);
    }
}
