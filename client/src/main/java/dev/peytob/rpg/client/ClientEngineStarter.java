package dev.peytob.rpg.client;

import dev.peytob.rpg.client.fsm.MockEngineState;
import dev.peytob.rpg.engine.pipeline.InitializingPipeline;
import org.springframework.stereotype.Component;

@Component
public class ClientEngineStarter {

    private final ClientEngine clientEngine;

    private final InitializingPipeline initializingPipeline;

    public ClientEngineStarter(ClientEngine clientEngine, InitializingPipeline initializingPipeline) {
        this.clientEngine = clientEngine;
        this.initializingPipeline = initializingPipeline;
    }

    void initializeAndStartGameCycle() {
        initializingPipeline.execute();
        clientEngine.runCycle(new MockEngineState());
    }
}
