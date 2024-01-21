package dev.peytob.rpg.client.loader.event.handler;

import dev.peytob.rpg.client.fsm.event.instance.AfterEngineStatePushEvent;
import dev.peytob.rpg.client.fsm.state.EngineState;
import dev.peytob.rpg.client.fsm.state.GameLoadingState;
import dev.peytob.rpg.core.gameplay.ecs.component.AsyncTaskComponent;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.engine.pipeline.InitializingPipeline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class GameLoadingStateLoadingStarterEventHandler {

    private final GameLoadingState gameLoadingState;

    private final InitializingPipeline initializingPipeline;

    @EventListener
    void handleEvent(AfterEngineStatePushEvent event) {
        EngineState engineState = event.executingEngineState().engineState();

        if (!gameLoadingState.equals(engineState)) {
            return;
        }

        EcsContext ecsContext = event.executingEngineState().ecsContext();

        log.info("Creating initializing pipeline async task entity");
        Entity loadingTaskEntity = ecsContext.createEntity();

        CompletableFuture<Void> loadingFuture = CompletableFuture
            .runAsync(initializingPipeline::execute);

        AsyncTaskComponent<Void> loadingTaskComponent =
            new AsyncTaskComponent<>(loadingFuture, "Initializing pipeline task");
        loadingTaskEntity.bindComponent(loadingTaskComponent);
    }
}
