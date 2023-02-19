package dev.peytob.rpg.engine.context.template;

import dev.peytob.rpg.ecs.system.OrderedSystem;

import java.util.Collection;

public interface EcsContextTemplate {

    Collection<OrderedSystem> getDefaultContextSystems();
}
