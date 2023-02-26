package dev.peytob.rpg.ecs.event;

import dev.peytob.rpg.ecs.context.EcsContext;

public interface EventHandler<T extends Event> {

    void handle(EcsContext context, T event);
}
