package dev.peytob.rpg.engine;

import dev.peytob.rpg.engine.pipeline.InitializingPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RpgEngine {

    private final Logger logger = LoggerFactory.getLogger(RpgEngine.class);

    private final InitializingPipeline initializingPipeline;

    public RpgEngine(InitializingPipeline initializingPipeline) {
        this.initializingPipeline = initializingPipeline;
    }

    public final void initialize() {
        logger.info("Starting engine initializing pipeline");
        initializingPipeline.execute();
        logger.info("Engine initializing pipeline completed");
    }
}
