package dev.peytob.rpg.engine;

import dev.peytob.rpg.engine.pipeline.InitializingPipeline;
import dev.peytob.rpg.engine.state.EngineState;
import dev.peytob.rpg.engine.state.EngineStateManager;
import dev.peytob.rpg.engine.utils.ExitCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public final class Engine {

    private final static Logger logger = LoggerFactory.getLogger(Engine.class);

    private final InitializingPipeline initializingPipeline;

    private final EngineFrameExecutor frameExecutor;

    private final EngineRunningChecker runningChecker;

    private final EngineStateManager engineStateManager;

    public Engine(InitializingPipeline initializingPipeline, EngineFrameExecutor frameExecutor, EngineRunningChecker runningChecker, EngineStateManager engineStateManager) {
        this.initializingPipeline = initializingPipeline;
        this.frameExecutor = frameExecutor;
        this.runningChecker = runningChecker;
        this.engineStateManager = engineStateManager;
    }

    public void initialize() {
        logger.info("Starting engine initializing pipeline");
        initializingPipeline.execute();
        logger.info("Engine initializing pipeline completed");
    }

    public void updateEngineState(EngineState engineState) {
        engineStateManager.updateEngineState(engineState);
    }

    public ExitCode runCycle() {
        if (!engineStateManager.isStateInitialized()) {
            throw new IllegalStateException("Engine state should be initialized before game cycle start!");
        }

        while (runningChecker.isEngineRunning()) {
            try {
                frameExecutor.executeFrame();
            } catch (Exception exception) {
                logger.error("Unhandled error while executing engine frame", exception);
                return ExitCode.FAILED;
            }
        }

        return ExitCode.SUCCESS;
    }
}
