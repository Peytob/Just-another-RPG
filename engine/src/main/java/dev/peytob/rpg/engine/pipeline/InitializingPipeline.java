package dev.peytob.rpg.engine.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class InitializingPipeline {

    private final Logger logger = LoggerFactory.getLogger(InitializingPipeline.class);

    private final List<InitializingPipelineStep> pipelineSteps;

    public InitializingPipeline(List<InitializingPipelineStep> pipelineSteps) {
        this.pipelineSteps = List.copyOf(pipelineSteps);
    }

    public void execute() {
        pipelineSteps.forEach(step -> {
            logger.info("Executing engine pipeline initializing step {}", step.getName());
            step.execute();
            logger.info("Initializing step {} was executed", step.getName());
        });
    }
}
