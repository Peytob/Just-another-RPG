package dev.peytob.rpg.ecs.entity;

import java.util.Collection;

public interface EntityManager {

    Entity getById(String id);

    void register(Entity entity);

    boolean remove(Entity entity);

    Collection<Entity> getAll();

    int getSize();

    void clear();
}
