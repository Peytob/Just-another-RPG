package dev.peytob.rpg.engine.context.initializer.entity;

import dev.peytob.rpg.ecs.entity.Entity;

/**
 * Injects components to entity.
 * It will be used while initializing game state to create base ECS system entities, if necessary.
 */
public interface SystemEntityComponentInitializer {

    void inject(Entity entity);

    String getId();
}
