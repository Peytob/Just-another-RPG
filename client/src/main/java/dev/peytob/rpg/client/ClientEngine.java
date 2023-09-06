package dev.peytob.rpg.client;

import dev.peytob.rpg.client.fsm.EngineState;
import dev.peytob.rpg.client.fsm.event.instance.BeforeEngineStateChangeEvent;
import dev.peytob.rpg.client.fsm.event.instance.BeforeEngineStateSetEvent;
import dev.peytob.rpg.client.fsm.event.instance.OnEngineStateSetEvent;
import dev.peytob.rpg.client.graphic.model.glfw.Window;
import dev.peytob.rpg.client.utils.ExitCode;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.context.EcsContextBuilder;
import dev.peytob.rpg.ecs.context.EcsContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public final class ClientEngine {

    private static final Logger logger = LoggerFactory.getLogger(ClientEngine.class);

    private final Window window;

    private EcsContext ecsContext;

    private final ApplicationEventPublisher applicationEventPublisher;

    private EngineState engineState;

    private EngineState nextEngineState;

    public ClientEngine(Window window, ApplicationEventPublisher applicationEventPublisher) {
        this.window = window;
        this.ecsContext = EcsContexts.empty();
        this.applicationEventPublisher = applicationEventPublisher;
        this.engineState = null;
        this.nextEngineState = null;
    }

    public ExitCode runCycle(EngineState startEngineState) {
        updateEngineState(startEngineState);

        while (!window.isShouldClose()) {
            if (nextEngineState != null) {
                updateEngineState(nextEngineState);
                nextEngineState = null;
            }

            window.pollEvents();

            try {
//                ecsContextManager.executeSystems();
                ecsContext.clearEvents();
            } catch (Exception exception) {
                logger.error("Unhandled error while executing engine frame", exception);
                return ExitCode.FAILED;
            }
        }

        return ExitCode.SUCCESS;
    }

    private void updateEngineState(EngineState newEngineState) {
        Objects.requireNonNull(newEngineState, "Engine state should be specified");

        if (engineState != null) {
            logger.info("Updating engine state from {} to {}", engineState.getName(), newEngineState.getName());
            applicationEventPublisher.publishEvent(new BeforeEngineStateChangeEvent(ecsContext, engineState));
        } else {
            logger.info("Setting first engine state: {}", newEngineState.getName());
        }

        EcsContextBuilder builder = EcsContexts.builder();
        applicationEventPublisher.publishEvent(new BeforeEngineStateSetEvent(builder, newEngineState));
        this.engineState = newEngineState;
        ecsContext = builder.build();
        applicationEventPublisher.publishEvent(new OnEngineStateSetEvent(ecsContext, engineState));

        if (logger.isDebugEnabled()) {
            String systemsList = ecsContext.getSystems().stream()
                .map(system -> system.getClass().getSimpleName())
                .collect(Collectors.joining(";\n"));

            logger.debug("Systems order in loaded ECS context for state:\n{}", systemsList);
        }
    }
}
