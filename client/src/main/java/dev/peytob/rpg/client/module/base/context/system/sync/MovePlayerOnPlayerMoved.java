package dev.peytob.rpg.client.module.base.context.system.sync;

import dev.peytob.rpg.client.fsm.annotation.IncludeInState;
import dev.peytob.rpg.client.fsm.state.instance.InGameEngineState;
import dev.peytob.rpg.client.module.base.context.event.PlayerMovedEvent;
import dev.peytob.rpg.client.module.network.service.PlayerMovingService;
import dev.peytob.rpg.core.module.base.context.component.position.PositionComponent;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.client.module.base.context.constant.DefaultContextEntitiesIds.PLAYER_ENTITY_ID;
import static dev.peytob.rpg.ecs.model.SystemDefaultOrder.INPUT_HANDLING;

@Component
@IncludeInState(state = InGameEngineState.class, order = INPUT_HANDLING + 1)
public class MovePlayerOnPlayerMoved implements System {

    private final PlayerMovingService playerMovingService;

    public MovePlayerOnPlayerMoved(PlayerMovingService playerMovingService) {
        this.playerMovingService = playerMovingService;
    }

    @Override
    public void execute(EcsContext context) {
        context.getEventsByType(PlayerMovedEvent.class).forEach(event -> movePlayer(context, event));
    }

    private void movePlayer(EcsContext context, PlayerMovedEvent event) {
        playerMovingService.directionalMove(event.normalizedDirection());

        // TODO Remove it later
        Entity playerEntity = context.getEntityById(PLAYER_ENTITY_ID).orElseThrow();
        PositionComponent playerPosition = playerEntity.getComponent(PositionComponent.class);
        playerPosition.setPosition(playerPosition.getPosition().plus(event.normalizedDirection().multiply(3.0f)));
    }
}
