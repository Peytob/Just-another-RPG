package dev.peytob.rpg.ecs.entity;

import java.util.Collection;

final class UnmodifiableEntityManager implements EntityManager {

    private final EntityManager entityManager;

    UnmodifiableEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void register(Entity entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Entity getById(String id) {
        return entityManager.getById(id);
    }

    @Override
    public boolean remove(Entity entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Entity> getAll() {
        return entityManager.getAll();
    }

    @Override
    public int getSize() {
        return entityManager.getSize();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
