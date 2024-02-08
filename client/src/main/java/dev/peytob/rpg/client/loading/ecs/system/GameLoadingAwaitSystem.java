package dev.peytob.rpg.client.loading.ecs.system;

import dev.peytob.rpg.client.fsm.annotation.IncludeInState;
import dev.peytob.rpg.client.fsm.service.EngineStateManager;
import dev.peytob.rpg.client.fsm.state.GameLoadingState;
import dev.peytob.rpg.client.fsm.state.InGameState;
import dev.peytob.rpg.core.gameplay.ecs.component.AsyncTaskComponent;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.ecs.system.System;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Component
@IncludeInState(GameLoadingState.class)
@RequiredArgsConstructor
@Slf4j
public class GameLoadingAwaitSystem implements System {

    private final EngineStateManager engineStateManager;

    private final InGameState inGameState;

    @Override
    public void execute(EcsContext context) {
        Collection<AsyncTaskComponent> asyncTasks = context.getComponentsByType(AsyncTaskComponent.class);

        if (asyncTasks.isEmpty()) {
            engineStateManager.chargeEngineState(inGameState);
            return;
        }

        for (AsyncTaskComponent<?> asyncTask : asyncTasks) {
            Entity componentEntity = context.getComponentEntity(asyncTask);

            String taskName = asyncTask.getTaskName();
            CompletableFuture<?> future = asyncTask.getFuture();

            if (future.isCompletedExceptionally()) {
                Throwable futureException = getFutureException(future);
                log.error("Exception while executing async task '{}'", taskName, futureException);
            }

            if (future.isCancelled()) {
                log.warn("Async task '{}' was cancelled while executing", taskName);
            }

            if (future.isDone()) {
                log.info("Async task '{} was executed, removing task components from context", taskName);
                componentEntity.removeComponent(asyncTask.getClass());
            }
        }
    }

    private Throwable getFutureException(Future<?> future) {
        try {
            future.get();
            return null;
        } catch (Exception e) {
            return e;
        }
    }
}
