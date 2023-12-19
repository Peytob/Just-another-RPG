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

    public void execute() {
        log.info("Executing engine initializing pipeline");

        pipelineSteps.forEach(step -> {
            log.info("Executing engine pipeline initializing step {}", step.getName());
            step.execute();
            log.info("Initializing step {} was executed", step.getName());
        });

        log.info("Engine initializing pipeline has been executed");
    }
}
