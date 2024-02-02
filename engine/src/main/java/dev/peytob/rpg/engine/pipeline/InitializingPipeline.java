package dev.peytob.rpg.engine.pipeline;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public final class InitializingPipeline {

    private final List<InitializingPipelineStep> pipelineSteps;

    private boolean isExecuted = false;

    public void execute() {
        if (isExecuted) {
            log.warn("Engine initializing pipeline is already executed, skipping...");
            return;
        }

        log.info("Executing engine initializing pipeline");

        pipelineSteps.forEach(step -> {
            log.info("Executing engine pipeline initializing step {}", step.getName());
            step.execute();
            log.info("Initializing step {} was executed", step.getName());
        });

        this.isExecuted = true;

        log.info("Engine initializing pipeline has been executed");
    }

    public boolean isExecuted() {
        return isExecuted;
    }
}
