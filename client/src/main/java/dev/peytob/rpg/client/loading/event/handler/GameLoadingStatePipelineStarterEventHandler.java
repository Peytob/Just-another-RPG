package dev.peytob.rpg.client.loading.event.handler;

import dev.peytob.rpg.client.fsm.event.instance.AfterEngineStatePushEvent;
import dev.peytob.rpg.client.fsm.event.instance.AfterInitializingPipelineExecuteEvent;
import dev.peytob.rpg.client.fsm.event.instance.BeforeInitializingPipelineExecuteEvent;
import dev.peytob.rpg.client.fsm.state.EngineState;
import dev.peytob.rpg.client.fsm.state.GameLoadingState;
import dev.peytob.rpg.core.gameplay.ecs.component.AsyncTaskComponent;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.engine.pipeline.InitializingPipeline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class GameLoadingStatePipelineStarterEventHandler {

    private final GameLoadingState gameLoadingState;

    private final InitializingPipeline initializingPipeline;

    private final ApplicationEventPublisher applicationEventPublisher;

    @EventListener
    void handleEvent(AfterEngineStatePushEvent event) {
        EngineState engineState = event.executingEngineState().engineState();

        if (!gameLoadingState.equals(engineState)) {
            return;
        }

        EcsContext ecsContext = event.executingEngineState().ecsContext();

        log.info("Creating initializing pipeline async task entity");
        Entity loadingTaskEntity = ecsContext.createEntity();

        applicationEventPublisher.publishEvent(new BeforeInitializingPipelineExecuteEvent());
        CompletableFuture<Void> loadingFuture = CompletableFuture
            .runAsync(initializingPipeline::execute)
            .exceptionally(throwable -> {
                log.error("Exception while executing initializing pipeline", throwable);
                return null;
            })
            .whenComplete((result, exception) -> applicationEventPublisher
                .publishEvent(new AfterInitializingPipelineExecuteEvent()));

        AsyncTaskComponent<Void> loadingTaskComponent =
            new AsyncTaskComponent<>(loadingFuture, "Initializing pipeline task");
        loadingTaskEntity.bindComponent(loadingTaskComponent);
    }
}
