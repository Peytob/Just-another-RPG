package dev.peytob.rpg.client;

import dev.peytob.rpg.client.fsm.EngineState;
import dev.peytob.rpg.client.fsm.model.ExecutingEngineState;
import dev.peytob.rpg.client.fsm.service.EngineStateManager;
import dev.peytob.rpg.client.graphic.model.glfw.Window;
import dev.peytob.rpg.client.utils.ExitCode;
import dev.peytob.rpg.ecs.context.EcsContext;
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
        engineStateManager.pushEngineState(startEngineState);

        while (!window.isShouldClose()) {
            engineStateManager.flushEngineStates();

            if (!engineStateManager.isStatePresent()) {
                logger.info("Engine state not present. Starting engine shutdown process");
                return shutdown();
            }

            ExecutingEngineState currentEngineState = engineStateManager.getCurrentEngineState();

            window.pollEvents();

            try {
                EcsContext ecsContext = currentEngineState.ecsContext();
                ecsContext.getSystems().forEach(system -> system.execute(ecsContext));
            } catch (Exception exception) {
                logger.error("Unhandled exception while executing engine frame", exception);
                return ExitCode.FAILED;
            }
        }

        return ExitCode.SUCCESS;
    }

    private ExitCode shutdown() {
        return ExitCode.SUCCESS;
    }
}
