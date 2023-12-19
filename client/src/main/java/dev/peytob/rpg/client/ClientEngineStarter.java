package dev.peytob.rpg.client;

import dev.peytob.rpg.client.fsm.state.GameLoadingState;
import dev.peytob.rpg.engine.pipeline.InitializingPipeline;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientEngineStarter {

    private final ClientEngine clientEngine;

    private final GameLoadingState gameLoadingState;

    private final InitializingPipeline initializingPipeline;

    void initializeAndStartGameCycle() {
        initializingPipeline.execute();
        clientEngine.runCycle(gameLoadingState);
    }
}
