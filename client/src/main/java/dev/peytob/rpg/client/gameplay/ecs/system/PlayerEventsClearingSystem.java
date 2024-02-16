package dev.peytob.rpg.client.gameplay.ecs.system;

import dev.peytob.rpg.client.fsm.annotation.IncludeInState;
import dev.peytob.rpg.client.fsm.state.InGameState;
import dev.peytob.rpg.client.gameplay.ecs.event.PlayerMovingEvent;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.ecs.model.SystemDefaultOrder.AFTER_ALL;

@Component
@IncludeInState(value = InGameState.class, order = AFTER_ALL)
public class PlayerEventsClearingSystem implements System {

    @Override
    public void execute(EcsContext context) {
        context.removeAllEventsByType(PlayerMovingEvent.class);
    }
}
