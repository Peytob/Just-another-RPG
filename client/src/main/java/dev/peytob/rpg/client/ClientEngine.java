package dev.peytob.rpg.client;

import dev.peytob.rpg.client.fsm.EngineState;
import dev.peytob.rpg.client.fsm.service.EngineStateManager;
import dev.peytob.rpg.client.graphic.model.glfw.Window;
import dev.peytob.rpg.client.utils.ExitCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public final class ClientEngine {

    private static final Logger logger = LoggerFactory.getLogger(ClientEngine.class);

    private final Window window;

    private final EngineStateManager engineStateManager;

    public ClientEngine(Window window, EngineStateManager engineStateManager) {
        this.window = window;
        this.engineStateManager = engineStateManager;
    }

    public ExitCode runCycle(EngineState startEngineState) {
        engineStateManager.changeState(startEngineState);

        while (!window.isShouldClose()) {
            engineStateManager.updateState();
            window.pollEvents();

            try {
                engineStateManager.executeFrameSystems();
            } catch (Exception exception) {
                logger.error("Unhandled error while executing engine frame", exception);
                return ExitCode.FAILED;
            }
        }

        return ExitCode.SUCCESS;
    }
}
