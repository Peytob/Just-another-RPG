package dev.peytob.rpg.client;

import dev.peytob.rpg.client.fsm.EngineState;
import dev.peytob.rpg.client.graphic.model.glfw.Window;
import dev.peytob.rpg.client.utils.ExitCode;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.context.EcsContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public final class ClientEngine {

    private static final Logger logger = LoggerFactory.getLogger(ClientEngine.class);

    private final Window window;

    private EngineState engineState;

    private EngineState nextEngineState;

    public ExitCode runCycle(EngineState startEngineState) {
        updateEngineState(startEngineState);

        while (!window.isShouldClose()) {
            if (nextEngineState != null) {
                updateEngineState(nextEngineState);
                nextEngineState = null;
            }

            window.pollEvents();

            try {
                ecsContextManager.executeSystems();
            } catch (Exception exception) {
                logger.error("Unhandled error while executing engine frame", exception);
                return ExitCode.FAILED;
            }
        }

        return ExitCode.SUCCESS;
    }

    private void updateEngineState(EngineState newEngineState) {
        Objects.requireNonNull(newEngineState, "Engine state should be specified");
        logger.info("Updating engine state from {} to {}", Objects.requireNonNullElse(engineState, ), newEngineState.getClass().getSimpleName());

        EcsContext prevContext;
        if (engineState != null) {
            engineState.beforeChange();
            prevContext = engineState.getEcsContext();
        } else {
            prevContext = EcsContexts.empty();
        }

        newEngineState.beforeSet(prevContext);
        this.engineState = newEngineState;
        newEngineState.onSet();

        if (logger.isDebugEnabled()) {
            String systemsList = engineState.getEcsContext().getSystems().stream()
                .map(system -> system.getClass().getSimpleName())
                .collect(Collectors.joining("\n"));

            logger.debug("Systems order in loaded ECS context for state:\n{}", systemsList);
        }
    }
}
