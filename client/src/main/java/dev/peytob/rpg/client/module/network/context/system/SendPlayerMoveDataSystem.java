package dev.peytob.rpg.client.module.network.context.system;

import dev.peytob.rpg.client.fsm.annotation.IncludeInState;
import dev.peytob.rpg.client.fsm.state.instance.InGameEngineState;
import dev.peytob.rpg.client.module.base.context.event.PlayerMovedEvent;
import dev.peytob.rpg.client.module.network.service.PlayerMovingService;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.ecs.model.SystemDefaultOrder.INPUT_HANDLING;

@Component
@IncludeInState(state = InGameEngineState.class, order = INPUT_HANDLING + 1)
public class SendPlayerMoveDataSystem implements System {

    private final PlayerMovingService playerMovingService;

    public SendPlayerMoveDataSystem(PlayerMovingService playerMovingService) {
        this.playerMovingService = playerMovingService;
    }

    @Override
    public void execute(EcsContext context) {
        context.getEventsByType(PlayerMovedEvent.class).forEach(event -> playerMovingService.directionalMove(event.normalizedDirection()));
    }
}
