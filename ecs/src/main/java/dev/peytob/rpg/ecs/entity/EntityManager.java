package dev.peytob.rpg.ecs.entity;

import java.util.Collection;

public interface EntityManager {

    void register(Entity entity);

    boolean remove(Entity entity);

    Entity getById(String entityId);

    Collection<Entity> getAll();

    int getSize();

    void clear();
}
