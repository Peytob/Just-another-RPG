package dev.peytob.rpg.engine.state;

import dev.peytob.rpg.ecs.context.EcsContextBuilder;
import dev.peytob.rpg.ecs.context.EcsContexts;
import dev.peytob.rpg.engine.context.EcsContextManager;
import dev.peytob.rpg.engine.event.EngineEventBus;
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

    private final EngineEventBus engineEventBus;

    private EngineState currentEngineState;

    public EngineStateManager(EcsContextManager ecsContextManager, EngineEventBus engineEventBus) {
        this.ecsContextManager = ecsContextManager;
        this.engineEventBus = engineEventBus;
        this.currentEngineState = null;
    }

    public void updateEngineState(EngineState engineState) {
        Objects.requireNonNull(engineState, "Engine state cant be null!");

        logger.info("Start updating engine state to {}", engineState.getName());

        EcsContextBuilder ecsContextBuilder = EcsContexts.builder();

        if (currentEngineState != null) {
            logger.info("Tearing down previous engine state");
            StateTearDownEvent stateTearDown = new StateTearDownEvent(currentEngineState, ecsContextBuilder, ecsContextManager.getRawEcsContext());
            engineEventBus.publishEvent(stateTearDown);
        }

        StateSetUpEvent stateSetUpEvent = new StateSetUpEvent(engineState, ecsContextBuilder);
        engineEventBus.publishEvent(stateSetUpEvent);
        ecsContextManager.refreshContext(ecsContextBuilder);

        this.currentEngineState = engineState;

        logger.info("Engine state has been updated");
    }

    public boolean isStateInitialized() {
        return currentEngineState != null;
    }
}
