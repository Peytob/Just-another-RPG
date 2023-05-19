package dev.peytob.rpg.engine.state;

import dev.peytob.rpg.ecs.context.EcsContextBuilder;
import dev.peytob.rpg.ecs.context.EcsContexts;
import dev.peytob.rpg.engine.context.EcsContextManager;
import dev.peytob.rpg.engine.event.EventBus;
import dev.peytob.rpg.engine.state.event.instance.StateSetUpEvent;
import dev.peytob.rpg.engine.state.event.instance.StateTearDownEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public final class EngineStateManager {

    private static final Logger logger = LoggerFactory.getLogger(EngineStateManager.class);

    private final EcsContextManager ecsContextManager;

    private final EventBus eventBus;

    private EngineState currentEngineState;

    public EngineStateManager(EcsContextManager ecsContextManager, EventBus eventBus) {
        this.ecsContextManager = ecsContextManager;
        this.eventBus = eventBus;
        this.currentEngineState = null;
    }

    public void updateEngineState(EngineState engineState) {
        Objects.requireNonNull(engineState, "Engine state cant be null!");

        logger.info("Start updating engine state to {}", engineState.getName());

        if (currentEngineState != null) {
            logger.info("Tearing down previous engine state");
            StateTearDownEvent stateTearDown = new StateTearDownEvent(currentEngineState, ecsContextManager.getRawEcsContext());
            eventBus.addEvent(stateTearDown);
        }

        EcsContextBuilder ecsContextBuilder = EcsContexts.builder();
        StateSetUpEvent stateSetUpEvent = new StateSetUpEvent(engineState, ecsContextBuilder);
        eventBus.addEvent(stateSetUpEvent);
        ecsContextManager.refreshContext(ecsContextBuilder);

        this.currentEngineState = engineState;

        logger.info("Engine state has been updated");
    }

    public boolean isStateInitialized() {
        return currentEngineState != null;
    }
}
