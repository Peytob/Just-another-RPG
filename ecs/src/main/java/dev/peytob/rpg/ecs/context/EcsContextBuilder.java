package dev.peytob.rpg.ecs.context;

import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.ecs.system.OrderedSystem;
import dev.peytob.rpg.ecs.system.System;

import java.util.function.BiConsumer;

public interface EcsContextBuilder {

    EcsContextBuilder addSystem(System system, Integer order);

    EcsContextBuilder addSystem(OrderedSystem orderedSystem);

    EcsContextBuilder initializeEntity(BiConsumer<Entity, EcsContext> initializer);

    EcsContextBuilder initializeEntity(BiConsumer<Entity, EcsContext> initializer, String customId);

    EcsContext build();
}
