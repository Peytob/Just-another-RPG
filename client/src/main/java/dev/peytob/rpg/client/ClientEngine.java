package dev.peytob.rpg.client;

import dev.peytob.rpg.client.fsm.state.EngineState;
import dev.peytob.rpg.client.fsm.model.ExecutingEngineState;
import dev.peytob.rpg.client.fsm.service.EngineStateManager;
import dev.peytob.rpg.client.graphic.model.glfw.Window;
import dev.peytob.rpg.client.utils.ExitCode;
import dev.peytob.rpg.ecs.context.EcsContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public final class ClientEngine {

    private final Window window;

    private final EngineStateManager engineStateManager;

    public ExitCode runCycle(EngineState startEngineState) {
        engineStateManager.pushEngineState(startEngineState);

        while (!window.isShouldClose()) {
            engineStateManager.flushEngineStates();

            if (!engineStateManager.isStatePresent()) {
                log.info("Engine state not present. Starting engine shutdown process");
                return shutdown();
            }

            ExecutingEngineState currentEngineState = engineStateManager.getCurrentEngineState();

            window.pollEvents();

            try {
                EcsContext ecsContext = currentEngineState.ecsContext();
                ecsContext.getSystems().forEach(system -> system.execute(ecsContext));
            } catch (Exception exception) {
                log.error("Unhandled exception while executing engine frame", exception);
                return ExitCode.FAILED;
            }
        }

        return ExitCode.SUCCESS;
    }

    private ExitCode shutdown() {
        return ExitCode.SUCCESS;
    }
}
