package dev.peytob.rpg.engine.state;

import dev.peytob.rpg.ecs.context.EcsContextBuilder;
import dev.peytob.rpg.ecs.context.EcsContexts;
import dev.peytob.rpg.engine.context.EcsContextManager;
import dev.peytob.rpg.engine.state.event.StateSetUpEventBus;
import dev.peytob.rpg.engine.state.event.StateTearDownEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public final class EngineStateManager {

    private static final Logger logger = LoggerFactory.getLogger(EngineStateManager.class);

    private final EcsContextManager ecsContextManager;

    private final StateTearDownEventBus stateTearDownEventBus;

    private final StateSetUpEventBus stateSetUpEventBus;

    private EngineState currentEngineState;

    public EngineStateManager(EcsContextManager ecsContextManager, StateTearDownEventBus stateTearDownEventBus, StateSetUpEventBus stateSetUpEventBus) {
        this.ecsContextManager = ecsContextManager;
        this.stateTearDownEventBus = stateTearDownEventBus;
        this.stateSetUpEventBus = stateSetUpEventBus;
        this.currentEngineState = null;
    }

    public void updateEngineState(EngineState engineState) {
        Objects.requireNonNull(engineState, "Engine state cant be null!");

        logger.info("Start updating engine state to {}", engineState.getName());

        if (currentEngineState != null) {
            logger.info("Tearing down previous engine state");
            stateTearDownEventBus.onStateTearDown(engineState, ecsContextManager.getRawEcsContext());
        }

        EcsContextBuilder ecsContextBuilder = EcsContexts.builder();
        stateSetUpEventBus.onStateSetUp(ecsContextBuilder, engineState);
        ecsContextManager.refreshContext(ecsContextBuilder);

        this.currentEngineState = engineState;

        logger.info("Engine state has been updated");
    }

    public boolean isStateInitialized() {
        return currentEngineState != null;
    }
}
