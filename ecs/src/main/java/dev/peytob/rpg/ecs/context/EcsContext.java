package dev.peytob.rpg.ecs.context;

import dev.peytob.rpg.ecs.entity.Entity;

public interface EcsContext {

    Entity newEntity(String id);

    boolean removeEntity(Entity entity);

    void clearEntities();
}
