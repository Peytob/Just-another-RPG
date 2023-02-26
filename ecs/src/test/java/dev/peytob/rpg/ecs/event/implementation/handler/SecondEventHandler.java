package dev.peytob.rpg.ecs.event.implementation.handler;

import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.event.EventHandler;
import dev.peytob.rpg.ecs.event.implementation.event.SecondEvent;

public class SecondEventHandler implements EventHandler<SecondEvent> {

    @Override
    public void handle(EcsContext context, SecondEvent event) {
        // Nothing...
    }
}
