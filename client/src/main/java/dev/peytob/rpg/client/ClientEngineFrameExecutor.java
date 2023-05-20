package dev.peytob.rpg.client;

import dev.peytob.rpg.engine.EngineFrameExecutor;
import dev.peytob.rpg.engine.context.EcsContextManager;
import org.springframework.stereotype.Component;

@Component
public final class ClientEngineFrameExecutor implements EngineFrameExecutor {

    private final EcsContextManager ecsContextManager;

    public ClientEngineFrameExecutor(EcsContextManager ecsContextManager) {
        this.ecsContextManager = ecsContextManager;
    }

    @Override
    public void executeFrame() {
        ecsContextManager.executeSystems();
    }
}
