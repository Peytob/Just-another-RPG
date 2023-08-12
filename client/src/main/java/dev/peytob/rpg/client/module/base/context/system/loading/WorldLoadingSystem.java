package dev.peytob.rpg.client.module.base.context.system.loading;

import dev.peytob.rpg.client.context.component.CopyEntityOnChangeStateFlag;
import dev.peytob.rpg.client.fsm.annotation.IncludeInState;
import dev.peytob.rpg.client.fsm.state.instance.InGameLoadingState;
import dev.peytob.rpg.client.module.base.service.world.WorldService;
import dev.peytob.rpg.core.context.component.template.AsyncTaskComponent;
import dev.peytob.rpg.core.module.base.context.component.world.TilemapComponent;
import dev.peytob.rpg.core.module.base.model.world.World;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.ecs.system.System;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static dev.peytob.rpg.client.module.base.context.system.constant.DefaultBaseLoadingEntities.WORLD_LOADING_ENTITY;

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
        Optional<Entity> asyncTaskEntity = context.getEntityById(WORLD_LOADING_ENTITY);

        if (asyncTaskEntity.isEmpty()) {
            CompletableFuture<World> future = worldService.loadWorld().whenComplete((world, throwable) -> {
                if (throwable != null) {
                    log.error("An exception thrown while loading the world: ", throwable);
                    return;
                }

                log.info("World loaded successfully!");
                Entity worldEntity = context.createEntity("world");
                worldEntity.bindComponent(new TilemapComponent(world.tilemap()));
                worldEntity.bindComponent(new CopyEntityOnChangeStateFlag());
            });

            Entity tilemapAsyncLoadingTaskEntity = context.createEntity(WORLD_LOADING_ENTITY);
            tilemapAsyncLoadingTaskEntity.bindComponent(new AsyncTaskComponent<>(future));
        }
    }
}
