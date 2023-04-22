package dev.peytob.rpg.engine.context.template;

import dev.peytob.rpg.ecs.system.OrderedSystem;

import java.util.Collection;
import java.util.List;

public record EcsContextTemplate(
        Collection<OrderedSystem> defaultSystems
) {

    public EcsContextTemplate {
        defaultSystems = List.copyOf(defaultSystems);
    }
}
