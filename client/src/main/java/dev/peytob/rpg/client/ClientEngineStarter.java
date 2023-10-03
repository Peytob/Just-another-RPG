package dev.peytob.rpg.client;

import dev.peytob.rpg.client.fsm.state.GameLoadingState;
import dev.peytob.rpg.engine.pipeline.InitializingPipeline;
import org.springframework.stereotype.Component;

@Component
public class ClientEngineStarter {

    private final ClientEngine clientEngine;

    private final GameLoadingState gameLoadingState;

    private final InitializingPipeline initializingPipeline;

    public ClientEngineStarter(ClientEngine clientEngine, GameLoadingState gameLoadingState, InitializingPipeline initializingPipeline) {
        this.clientEngine = clientEngine;
        this.gameLoadingState = gameLoadingState;
        this.initializingPipeline = initializingPipeline;
    }

    void initializeAndStartGameCycle() {
        initializingPipeline.execute();
        clientEngine.runCycle(gameLoadingState);
    }
}
