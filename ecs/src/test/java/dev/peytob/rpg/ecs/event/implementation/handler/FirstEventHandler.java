package dev.peytob.rpg.ecs.event.implementation.handler;

import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.event.EventHandler;
import dev.peytob.rpg.ecs.event.implementation.event.FirstEvent;

public class FirstEventHandler implements EventHandler<FirstEvent> {
    @Override
    public void handle(EcsContext context, FirstEvent event) {
        // Nothing...
    }
}
