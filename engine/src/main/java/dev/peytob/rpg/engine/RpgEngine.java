package dev.peytob.rpg.engine;

import dev.peytob.rpg.engine.context.EcsContextManager;
import dev.peytob.rpg.engine.pipeline.InitializingPipeline;
import dev.peytob.rpg.engine.state.EngineState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RpgEngine {

    private final Logger logger = LoggerFactory.getLogger(RpgEngine.class);

    private final InitializingPipeline initializingPipeline;

    private final EcsContextManager ecsContextManager;

    public RpgEngine(InitializingPipeline initializingPipeline, EcsContextManager ecsContextManager) {
        this.initializingPipeline = initializingPipeline;
        this.ecsContextManager = ecsContextManager;
    }

    public final void initialize() {
        logger.info("Starting engine initializing pipeline");
        initializingPipeline.execute();
        logger.info("Engine initializing pipeline completed");
    }

    public final void updateEngineState(EngineState engineState) {
        logger.info("Start updating engine state");

        logger.info("Refreshing context state");
        ecsContextManager.refreshContext(engineState.getEcsContextTemplate());

        logger.info("Engine state has been updated");
    }
}
