package dev.peytob.rpg.engine.state;

import dev.peytob.rpg.engine.context.EcsContextManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public final class EngineStateManager {

    private static final Logger logger = LoggerFactory.getLogger(EngineStateManager.class);

    private final EcsContextManager ecsContextManager;

    private EngineState currentEngineState;

    public EngineStateManager(EcsContextManager ecsContextManager) {
        this.ecsContextManager = ecsContextManager;
        this.currentEngineState = null;
    }

    public void updateEngineState(EngineState engineState) {
        Objects.requireNonNull(engineState, "Engine state cant be null!");

        logger.info("Start updating engine state to {}", engineState.getName());

        if (currentEngineState != null) {
            logger.info("Tearing down previous engine state");
            currentEngineState.onTearDown(ecsContextManager.getRawEcsContext());
        }

        ecsContextManager.refreshContext();

        this.currentEngineState = engineState;

        logger.info("Engine state has been updated");
    }

    public boolean isStateInitialized() {
        return currentEngineState == null;
    }
}
