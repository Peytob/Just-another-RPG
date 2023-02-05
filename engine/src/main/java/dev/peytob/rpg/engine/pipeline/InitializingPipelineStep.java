package dev.peytob.rpg.engine.pipeline;

import org.springframework.core.Ordered;

public interface InitializingPipelineStep extends Ordered {

    void execute();

    default String getName() {
        return getClass().getSimpleName();
    }
}
