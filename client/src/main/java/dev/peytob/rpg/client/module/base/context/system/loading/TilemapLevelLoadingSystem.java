package dev.peytob.rpg.client.module.base.context.system.loading;

import dev.peytob.rpg.client.context.component.CopyEntityOnChangeStateFlag;
import dev.peytob.rpg.client.module.base.context.component.loading.TilemapAsyncLoadingComponent;
import dev.peytob.rpg.client.module.base.service.level.TilemapLoaderService;
import dev.peytob.rpg.core.module.base.context.component.level.TilemapComponent;
import dev.peytob.rpg.core.module.base.model.level.tilemap.Tilemap;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.Future;

@Component
public class TilemapLevelLoadingSystem implements System {

    private final AsyncTaskExecutor asyncTaskExecutor;

    private final TilemapLoaderService tilemapLoaderService;

    public TilemapLevelLoadingSystem(AsyncTaskExecutor asyncTaskExecutor, TilemapLoaderService tilemapLoaderService) {
        this.asyncTaskExecutor = asyncTaskExecutor;
        this.tilemapLoaderService = tilemapLoaderService;
    }

    @Override
    public void execute(EcsContext context) {
        Optional<TilemapAsyncLoadingComponent> asyncTask = context
            .getSingletonComponentByType(TilemapAsyncLoadingComponent.class);

        if (asyncTask.isEmpty()) {
            Future<?> future = asyncTaskExecutor.submit(() -> {
                Tilemap tilemap = tilemapLoaderService.loadTilemap("");
                Entity tilemapEntity = context.createEntity("tilemap");
                tilemapEntity.bindComponent(new TilemapComponent(tilemap));
                tilemapEntity.bindComponent(new CopyEntityOnChangeStateFlag());
            });

            Entity tilemapAsyncLoadingTaskEntity = context.createEntity("tilemap_async_loading_task");
            tilemapAsyncLoadingTaskEntity.bindComponent(new TilemapAsyncLoadingComponent(future));
        }
    }
}
