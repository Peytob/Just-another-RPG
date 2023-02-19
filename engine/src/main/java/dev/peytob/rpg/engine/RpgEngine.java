package dev.peytob.rpg.engine;

import dev.peytob.rpg.engine.context.EcsContextManager;
import dev.peytob.rpg.engine.pipeline.InitializingPipeline;
import dev.peytob.rpg.engine.state.EngineState;
import dev.peytob.rpg.engine.utils.ExitCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
public final class RpgEngine {

    private final Logger logger = LoggerFactory.getLogger(RpgEngine.class);

    private final InitializingPipeline initializingPipeline;

    private final EcsContextManager ecsContextManager;

    private final AtomicBoolean isRunning;

    public RpgEngine(InitializingPipeline initializingPipeline, EcsContextManager ecsContextManager) {
        this.initializingPipeline = initializingPipeline;
        this.ecsContextManager = ecsContextManager;

        this.isRunning = new AtomicBoolean(false);
    }

    public void initialize() {
        logger.info("Starting engine initializing pipeline");
        initializingPipeline.execute();
        logger.info("Engine initializing pipeline completed");
    }

    public void updateEngineState(EngineState engineState) {
        logger.info("Start updating engine state");

        logger.info("Refreshing context state");
        ecsContextManager.refreshContext(engineState.getEcsContextTemplate());

        logger.info("Engine state has been updated");
    }

    public void close() {
        this.isRunning.set(false);
    }

    public ExitCode runCycle() {
        isRunning.set(true);

        while (isRunning.get()) {
            try {
                ecsContextManager.executeSystems();
            } catch (Exception exception) {
                logger.error("Unhandled error while executing systems", exception);
                return ExitCode.FAILED;
            }
        }

        return ExitCode.SUCCESS;
    }
}
