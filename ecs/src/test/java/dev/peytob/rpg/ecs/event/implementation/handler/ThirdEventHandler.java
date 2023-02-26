package dev.peytob.rpg.ecs.event.implementation.handler;

import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.event.EventHandler;
import dev.peytob.rpg.ecs.event.implementation.event.ThirdEvent;

public class ThirdEventHandler implements EventHandler<ThirdEvent> {

    @Override
    public void handle(EcsContext context, ThirdEvent event) {
        // Nothing...
    }
}
