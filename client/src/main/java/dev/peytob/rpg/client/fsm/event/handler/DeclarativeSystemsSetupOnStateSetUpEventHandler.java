package dev.peytob.rpg.client.fsm.event.handler;

import dev.peytob.rpg.client.fsm.state.EngineState;
import dev.peytob.rpg.client.fsm.event.instance.BeforeEngineStatePushEvent;
import dev.peytob.rpg.client.fsm.service.StateSystemsManager;
import dev.peytob.rpg.ecs.context.EcsContextBuilder;
import dev.peytob.rpg.ecs.system.OrderedSystem;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class DeclarativeSystemsSetupOnStateSetUpEventHandler {

    private final StateSystemsManager stateSystemsManager;

    @EventListener
    void setupDeclarativeSystems(BeforeEngineStatePushEvent stateSetUpEvent) {
        EcsContextBuilder ecsContextBuilder = stateSetUpEvent.contextBuilder();
        EngineState engineState = stateSetUpEvent.engineState();
        Collection<OrderedSystem> systemsForState = stateSystemsManager.getSystemsForState(engineState);
        systemsForState.forEach(ecsContextBuilder::addSystem);
    }
}
