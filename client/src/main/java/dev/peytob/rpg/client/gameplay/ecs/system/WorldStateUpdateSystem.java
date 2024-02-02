package dev.peytob.rpg.client.gameplay.ecs.system;

import dev.peytob.rpg.client.fsm.annotation.IncludeInState;
import dev.peytob.rpg.client.fsm.state.InGameState;
import dev.peytob.rpg.client.gameplay.ecs.event.WorldUpdatedEvent;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static dev.peytob.rpg.ecs.model.SystemDefaultOrder.WORLD_STATE_UPDATING;

@Component
@IncludeInState(value = InGameState.class, order = WORLD_STATE_UPDATING)
public class WorldStateUpdateSystem implements System {

    @Override
    public void execute(EcsContext context) {
        Collection<WorldUpdatedEvent> worldUpdatedEvents = context.getEventsByType(WorldUpdatedEvent.class);
        worldUpdatedEvents.forEach(event -> {
            handleEvent(event, context);
            context.removeEvent(event);
        });
    }

    private void handleEvent(WorldUpdatedEvent event, EcsContext context) {
        // TODO Handling...
    }
}
