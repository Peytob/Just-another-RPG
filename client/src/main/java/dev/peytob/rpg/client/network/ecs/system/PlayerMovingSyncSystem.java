package dev.peytob.rpg.client.network.ecs.system;

import dev.peytob.rpg.client.fsm.annotation.IncludeInState;
import dev.peytob.rpg.client.fsm.state.InGameState;
import dev.peytob.rpg.client.gameplay.ecs.event.PlayerMovingEvent;
import dev.peytob.rpg.core.gameplay.ecs.component.PositionComponent;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.model.SystemDefaultOrder;
import dev.peytob.rpg.ecs.system.System;
import dev.peytob.rpg.math.vector.Vec2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.client.gameplay.constant.DefaultClientEntityId.MAIN_CHARACTER_ENTITY_ID;

@Component
@IncludeInState(value = InGameState.class, order = SystemDefaultOrder.UPDATE_POOLING)
@Slf4j
public class PlayerMovingSyncSystem implements System {

    @Override
    public void execute(EcsContext context) {
        // TODO Send data to server...

        context.getEventsByType(PlayerMovingEvent.class).stream().findFirst().ifPresent(playerMovingEvent -> {
            context.getEntityById(MAIN_CHARACTER_ENTITY_ID)
                .map(entity -> entity.getComponent(PositionComponent.class))
                .ifPresent(positionComponent -> handleMoving(positionComponent, playerMovingEvent));
        });
    }

    private void handleMoving(PositionComponent positionComponent, PlayerMovingEvent playerMovingEvent) {
        Vec2 playerMovingDiff = playerMovingEvent.movingDirection().multiply(playerMovingEvent.movingSpeed());
        Vec2 newPosition = positionComponent.getPosition().plus(playerMovingDiff);
        log.debug("Moving from {} to {}", positionComponent.getPosition(), newPosition);
        positionComponent.setPosition(newPosition);
    }
}
