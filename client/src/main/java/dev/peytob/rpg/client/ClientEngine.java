package dev.peytob.rpg.client;

import dev.peytob.rpg.client.module.graphic.model.Window;
import dev.peytob.rpg.engine.context.EcsContextManager;
import dev.peytob.rpg.engine.state.EngineState;
import dev.peytob.rpg.engine.state.EngineStateManager;
import dev.peytob.rpg.engine.utils.ExitCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public final class ClientEngine {

    private static final Logger logger = LoggerFactory.getLogger(ClientEngine.class);

    private final EngineStateManager engineStateManager;

    private final EcsContextManager ecsContextManager;

    private final Window window;

    public ClientEngine(EngineStateManager engineStateManager, EcsContextManager ecsContextManager, Window window) {
        this.engineStateManager = engineStateManager;
        this.ecsContextManager = ecsContextManager;
        this.window = window;
    }

    public ExitCode runCycle(EngineState startEngineState) {
        Objects.requireNonNull(startEngineState, "Engine state should be specified before engine start");

        engineStateManager.updateEngineState(startEngineState);

        while (!window.isShouldClose()) {
            try {
                ecsContextManager.executeSystems();
            } catch (Exception exception) {
                logger.error("Unhandled error while executing engine frame", exception);
                return ExitCode.FAILED;
            }
        }

        return ExitCode.SUCCESS;
    }
}
