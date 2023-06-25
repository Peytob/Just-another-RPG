package dev.peytob.rpg.client.module.base.state.event;

import dev.peytob.rpg.client.module.base.context.system.EngineStateChangeSystem;
import dev.peytob.rpg.ecs.context.EcsContextBuilder;
import dev.peytob.rpg.engine.context.system.SystemFactory;
import dev.peytob.rpg.engine.state.event.instance.StateSetUpEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.ecs.model.SystemDefaultOrder.AFTER_ALL;

@Component
public class EventChangeSystemRegistering {

    private final SystemFactory systemFactory;

    public EventChangeSystemRegistering(SystemFactory systemFactory) {
        this.systemFactory = systemFactory;
    }

    @EventListener
    public void registerEventChangingSystem(StateSetUpEvent stateSetUpEvent) {
        EcsContextBuilder contextBuilder = stateSetUpEvent.contextBuilder();

        contextBuilder
            .addSystem(systemFactory.getSystem(EngineStateChangeSystem.class), AFTER_ALL);
    }
}
