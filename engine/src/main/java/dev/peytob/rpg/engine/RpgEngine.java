package dev.peytob.rpg.engine;

import dev.peytob.rpg.engine.context.EcsContextManager;
import dev.peytob.rpg.engine.context.template.EcsContextTemplate;
import dev.peytob.rpg.engine.pipeline.InitializingPipeline;
import dev.peytob.rpg.engine.state.EngineState;
import dev.peytob.rpg.engine.utils.ExitCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public final class RpgEngine {

    private final static Logger logger = LoggerFactory.getLogger(RpgEngine.class);

    private final InitializingPipeline initializingPipeline;

    private final EcsContextManager ecsContextManager;

    private final AtomicBoolean isRunning;

    private EngineState currentEngineState;

    public RpgEngine(InitializingPipeline initializingPipeline, EcsContextManager ecsContextManager) {
        this.initializingPipeline = initializingPipeline;
        this.ecsContextManager = ecsContextManager;
        this.currentEngineState = null;

        this.isRunning = new AtomicBoolean(false);
    }

    public void initialize() {
        logger.info("Starting engine initializing pipeline");
        initializingPipeline.execute();
        logger.info("Engine initializing pipeline completed");
    }

    public void updateEngineState(EngineState engineState) {
        Objects.requireNonNull(engineState, "Engine state cant be null!");

        logger.info("Start updating engine state to {}", engineState.getName());

        if (currentEngineState != null) {
            logger.info("Tearing down previous engine state");
            currentEngineState.onTearDown(ecsContextManager.getRawEcsContext());
        }

        logger.info("Refreshing context state");
        EcsContextTemplate ecsContextTemplate = createEcsContextTemplate(engineState);
        ecsContextManager.refreshContext(ecsContextTemplate);

        currentEngineState = engineState;

        logger.info("Setting up new engine state");
        currentEngineState.onSetUp(ecsContextManager.getRawEcsContext());

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

    private EcsContextTemplate createEcsContextTemplate(EngineState engineState) {
        return new EcsContextTemplate(engineState.getSystems());
    }
}
