package dev.peytob.rpg.client.fsm.service;

import dev.peytob.rpg.client.fsm.EngineState;
import dev.peytob.rpg.client.fsm.event.instance.AfterEngineStatePushEvent;
import dev.peytob.rpg.client.fsm.event.instance.BeforeEngineStatePopEvent;
import dev.peytob.rpg.client.fsm.event.instance.BeforeEngineStatePushEvent;
import dev.peytob.rpg.client.fsm.model.EngineStateOperation;
import dev.peytob.rpg.client.fsm.model.EngineStateOperationQueue;
import dev.peytob.rpg.client.fsm.model.EngineStateStack;
import dev.peytob.rpg.client.fsm.model.ExecutingEngineState;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.context.EcsContextBuilder;
import dev.peytob.rpg.ecs.context.EcsContexts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SimpleEngineStateManager implements EngineStateManager {

    private final ApplicationEventPublisher applicationEventPublisher;

    private final EngineStateStack engineStateStack;

    private final EngineStateOperationQueue engineStateOperationQueue;

    public SimpleEngineStateManager(ApplicationEventPublisher applicationEventPublisher, EngineStateStack engineStateStack, EngineStateOperationQueue engineStateOperationQueue) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.engineStateStack = engineStateStack;
        this.engineStateOperationQueue = engineStateOperationQueue;
    }

    @Override
    public void pushEngineState(EngineState engineState) {
        log.info("Pushing 'push {}' operation to engine states operation queue", engineState.getName());
        EngineStateOperation operation = EngineStateOperation.push(engineState);
        engineStateOperationQueue.push(operation);
    }

    @Override
    public void popEngineState() {
        log.info("Pushing 'pop' operation to engine states operation queue");
        EngineStateOperation operation = EngineStateOperation.pop();
        engineStateOperationQueue.push(operation);
    }

    @Override
    public void chargeEngineState(EngineState engineState) {
        popEngineState();
        pushEngineState(engineState);
    }

    @Override
    public void flushEngineStates() {
        while (engineStateOperationQueue.isNotEmpty()) {
            log.info("Flushing engine state operations");

            EngineStateOperation operation = engineStateOperationQueue.pop();

            // TODO Use pattern-matching after switching to JDK 21 ( when (operation) { ... } )

            if (operation instanceof EngineStateOperation.PopEngineStateOperation) {
                executePopEngineState();
            } else if (operation instanceof EngineStateOperation.PushEngineStateOperation push) {
                executePushEngineState(push.engineState());
            } else {
                log.error("Unknown engine state operation, skipping...");
            }
        }
    }

    @Override
    public boolean isStatePresent() {
        return engineStateStack.isNotEmpty();
    }

    @Override
    public ExecutingEngineState getCurrentEngineState() {
        if (!isStatePresent()) {
            log.warn("Empty engine states stack on 'getCurrentEngineState' call");
            return null;
        }

        return engineStateStack.peek();
    }

    private void executePopEngineState() {
        if (!isStatePresent()) {
            throw new IllegalStateException("State cannot be pop: state is not present");
        }

        ExecutingEngineState executingEngineState = engineStateStack.peek();
        log.info("Popping current engine state {}", executingEngineState.engineState().getName());

        BeforeEngineStatePopEvent beforeEngineStatePopEvent = new BeforeEngineStatePopEvent(executingEngineState);
        applicationEventPublisher.publishEvent(beforeEngineStatePopEvent);
        engineStateStack.pop();
    }

    private void executePushEngineState(EngineState engineState) {
        log.info("Pushing engine state {}", engineState.getName());

        EcsContextBuilder builder = EcsContexts.builder();
        BeforeEngineStatePushEvent beforeEngineStatePushEvent = new BeforeEngineStatePushEvent(builder, engineState);
        applicationEventPublisher.publishEvent(beforeEngineStatePushEvent);
        EcsContext ecsContext = builder.build();

        ExecutingEngineState executingEngineState = new ExecutingEngineState(engineState, ecsContext);
        engineStateStack.push(executingEngineState);
        AfterEngineStatePushEvent afterEngineStatePushEvent = new AfterEngineStatePushEvent(executingEngineState);
        applicationEventPublisher.publishEvent(afterEngineStatePushEvent);
    }
}
