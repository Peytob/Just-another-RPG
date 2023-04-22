package dev.peytob.rpg.ecs.entity;

import java.util.Collection;

public interface EntityManager {

    Entity getById(String id);

    void addEntity(Entity entity);

    boolean removeEntity(Entity entity);

    Entity getEntityById(String entityId);

    Collection<Entity> getAllEntities();

    int getEntitiesCount();

    void clear();
}
