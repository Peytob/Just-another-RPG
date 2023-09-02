package dev.peytob.rpg.client.module.network.context.system;

import dev.peytob.rpg.client.fsm.annotation.IncludeInState;
import dev.peytob.rpg.client.fsm.state.instance.InGameEngineState;
import dev.peytob.rpg.client.module.base.model.Player;
import dev.peytob.rpg.client.module.network.service.PlayerAccessorService;
import dev.peytob.rpg.client.module.network.service.PlayerMovingService;
import dev.peytob.rpg.core.context.component.template.AsyncTaskComponent;
import dev.peytob.rpg.core.module.base.context.component.position.PositionComponent;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static dev.peytob.rpg.client.module.base.context.constant.DefaultContextEntitiesIds.PLAYER_ENTITY_ID;
import static dev.peytob.rpg.ecs.model.SystemDefaultOrder.WORLD_STATE_UPDATING;

@Component
@IncludeInState(state = InGameEngineState.class, order = WORLD_STATE_UPDATING)
public class SyncPlayerDataSystem implements System {

    private final PlayerAccessorService playerAccessorService;

    public SyncPlayerDataSystem(PlayerAccessorService playerAccessorService) {
        this.playerAccessorService = playerAccessorService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void execute(EcsContext context) {
        Optional<Entity> playerEntity = context.getEntityById(PLAYER_ENTITY_ID);

        if (playerEntity.isEmpty()) {
            return;
        }

        AsyncTaskComponent<Player> currentPlayerAsyncTask = playerEntity.get().getComponent(AsyncTaskComponent.class);

        if (currentPlayerAsyncTask == null) {
            CompletableFuture<Player> meFuture = playerAccessorService.getMe();
            playerEntity.get().bindComponent(new AsyncTaskComponent<>(meFuture));
        } else {
            if (!currentPlayerAsyncTask.isDone()) {
                return;
            }

            try {
                Player player = currentPlayerAsyncTask.get();
                updatePlayerEntity(playerEntity.get(), player);
            } catch (InterruptedException | ExecutionException e) {
                throw new IllegalStateException("Player future is completed with exception", e);
            }
        }
    }

    private void updatePlayerEntity(Entity playerEntity, Player player) {
        PositionComponent positionComponent = playerEntity.getComponent(PositionComponent.class);
        positionComponent.setPosition(player.position());
    }
}
