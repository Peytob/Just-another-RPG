package dev.peytob.rpg.client.fsm.service;

import dev.peytob.rpg.client.fsm.EngineState;
import dev.peytob.rpg.client.fsm.event.instance.BeforeEngineStateChangeEvent;
import dev.peytob.rpg.client.fsm.event.instance.BeforeEngineStateSetEvent;
import dev.peytob.rpg.client.fsm.event.instance.OnEngineStateSetEvent;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.context.EcsContextBuilder;
import dev.peytob.rpg.ecs.context.EcsContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SimpleEngineStateManager implements EngineStateManager {

    private static final Logger logger = LoggerFactory.getLogger(SimpleEngineStateManager.class);

    private final ApplicationEventPublisher applicationEventPublisher;

    private EngineState engineState;

    private EngineState nextEngineState;

    private EcsContext ecsContext;

    public SimpleEngineStateManager(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
        ecsContext = EcsContexts.empty();
        engineState = null;
        nextEngineState = null;
    }

    @Override
    public void updateState() {
        if (nextEngineState != null) {
            performChangeState(nextEngineState);
            nextEngineState = null;
        }
    }

    @Override
    public void executeFrameSystems() {
        try {
            ecsContext.getSystems().forEach(system -> system.execute(ecsContext));
        } catch (Exception exception) {
            // TODO Make exception handlers
        }
    }

    @Override
    public void changeState(EngineState engineState) {
        this.nextEngineState = engineState;
    }

    private void performChangeState(EngineState newEngineState) {
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
