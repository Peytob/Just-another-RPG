package dev.peytob.rpg.server;

import dev.peytob.rpg.engine.pipeline.InitializingPipeline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InitializingPipelineStater implements CommandLineRunner {

    private final InitializingPipeline initializingPipeline;

    @Override
    public void run(String... args) {
        initializingPipeline.execute();
    }
}
