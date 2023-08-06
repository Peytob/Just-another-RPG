package dev.peytob.rpg.client.module.base.context.system.loading;

import dev.peytob.rpg.client.context.component.CopyEntityOnChangeStateFlag;
import dev.peytob.rpg.client.fsm.annotation.IncludeInState;
import dev.peytob.rpg.client.fsm.state.instance.InGameLoadingState;
import dev.peytob.rpg.client.module.base.context.component.loading.TilemapAsyncLoadingComponent;
import dev.peytob.rpg.client.module.base.service.world.WorldService;
import dev.peytob.rpg.core.module.base.context.component.level.TilemapComponent;
import dev.peytob.rpg.core.module.base.model.level.tilemap.Tilemap;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.ecs.system.System;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
@IncludeInState(state = InGameLoadingState.class)
public class WorldLoadingSystem implements System {

    private static final Logger log = LoggerFactory.getLogger(WorldLoadingSystem.class);

    private final WorldService worldService;

    public WorldLoadingSystem(WorldService worldService) {
        this.worldService = worldService;
    }

    @Override
    public void execute(EcsContext context) {
        Optional<TilemapAsyncLoadingComponent> asyncTask = context
            .getSingletonComponentByType(TilemapAsyncLoadingComponent.class);

        if (asyncTask.isEmpty()) {
            CompletableFuture<Tilemap> future = worldService.loadWorld().whenComplete((tilemap, throwable) -> {
                if (throwable != null) {
                    log.error("An exception thrown while loading the world: ", throwable);
                    return;
                }

                log.info("Tilemap loaded successfully!");
                Entity tilemapEntity = context.createEntity("tilemap");
                tilemapEntity.bindComponent(new TilemapComponent(tilemap));
                tilemapEntity.bindComponent(new CopyEntityOnChangeStateFlag());
            });

            Entity tilemapAsyncLoadingTaskEntity = context.createEntity("tilemap_async_loading_task");
            tilemapAsyncLoadingTaskEntity.bindComponent(new TilemapAsyncLoadingComponent(future));
        }
    }
}
