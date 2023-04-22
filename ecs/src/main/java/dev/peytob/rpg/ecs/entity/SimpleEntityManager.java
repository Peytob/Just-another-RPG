package dev.peytob.rpg.ecs.entity;

import dev.peytob.rpg.ecs.exception.EntityAlreadyRegisteredException;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This realisation just wraps synchronized map of entities
 */
final class SimpleEntityManager implements EntityManager {

    private final Map<String, Entity> entities;

    public SimpleEntityManager() {
        this.entities = Collections.synchronizedMap(new HashMap<>());
    }

    @Override
    public void addEntity(Entity entity) {
        if (entities.containsValue(entity) || entities.containsKey(entity.getId())) {
            throw new EntityAlreadyRegisteredException(entity);
        }

        entities.put(entity.getId(), entity);
    }

    @Override
    public Entity getById(String id) {
        return entities.get(id);
    }

    @Override
    public boolean removeEntity(Entity entity) {
        return entities.remove(entity.getId()) != null;
    }

    @Override
    public Collection<Entity> getAllEntities() {
        return Collections.unmodifiableCollection(entities.values());
    }

    @Override
    public Entity getEntityById(String entityId) {
        return entities.get(entityId);
    }

    @Override
    public int getEntitiesCount() {
        return entities.size();
    }

    @Override
    public void clear() {
        entities.clear();
    }
}
