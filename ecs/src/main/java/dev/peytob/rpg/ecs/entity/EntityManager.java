package dev.peytob.rpg.ecs.entity;

import java.util.Collection;

public interface EntityManager {

    void register(Entity entity);

    boolean remove(Entity entity);

    Collection<Entity> getAll();

    void clear();
}
