package dev.peytob.rpg.client.input.hid.input.system;

import dev.peytob.rpg.client.fsm.annotation.IncludeInAllStates;
import dev.peytob.rpg.client.input.hid.ecs.event.KeyboardKeyEvent;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.stereotype.Component;

import static dev.peytob.rpg.ecs.model.SystemDefaultOrder.AFTER_ALL;

@Component
@IncludeInAllStates(order = AFTER_ALL)
public class KeyboardEventsClearingSystem implements System {

    @Override
    public void execute(EcsContext context) {
        context.removeAllEventsByType(KeyboardKeyEvent.class);
    }
}
