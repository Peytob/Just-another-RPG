package dev.peytob.rpg.ecs.context;

import dev.peytob.rpg.ecs.component.ComponentManager;
import dev.peytob.rpg.ecs.component.ComponentManagers;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.ecs.entity.EntityManager;
import dev.peytob.rpg.ecs.entity.EntityManagers;
import dev.peytob.rpg.ecs.entity.GenericEntity;
import dev.peytob.rpg.ecs.entity.filer.FilterManager;
import dev.peytob.rpg.ecs.event.EventManager;
import dev.peytob.rpg.ecs.system.System;
import dev.peytob.rpg.ecs.system.SystemManager;

import java.util.List;

final class MutableEcsContext implements EcsContext {

    private final ComponentManager componentManager;

    private final EntityManager entityManager;

    private final SystemManager systemManager;

    private final EventManager eventManager;

    private final FilterManager filterManager;

    public MutableEcsContext(ComponentManager componentManager,
                             FilterManager filterManager,
                             EntityManager entityManager,
                             SystemManager systemManager,
                             EventManager eventManager) {
        this.componentManager = componentManager;
        this.entityManager = entityManager;
        this.systemManager = systemManager;
        this.eventManager = eventManager;
        this.filterManager = filterManager;
    }

    @Override
    public ComponentManager getUnmodifiableComponentManager() {
        return ComponentManagers.unmodifiable(componentManager);
    }

    @Override
    public EntityManager getUnmodifiableEntityManager() {
        return EntityManagers.unmodifiable(entityManager);
    }

    @Override
    public SystemManager getSystemManager() {
        return systemManager;
    }

    @Override
    public EventManager getEventManager() {
        return eventManager;
    }

    @Override
    public FilterManager getFilterManager() {
        return filterManager;
    }

    @Override
    public Entity newEntity(String id) {
        GenericEntity genericEntity = new GenericEntity(id);
        ContextEntity contextEntity = new ContextEntity(this, genericEntity);
        entityManager.register(contextEntity);
        return contextEntity;
    }

    @Override
    public boolean removeEntity(Entity entity) {
        entity
            .getComponents()
            .forEach(componentManager::remove);

        return entityManager.remove(entity);
    }

    @Override
    public void clearEntities() {
        entityManager.clear();
        componentManager.clear();
    }

    @Override
    public void executeSystems() {
        List<System> systems = List.copyOf(systemManager.getAll());
        systems.forEach(system -> system.execute(this));
    }

    ComponentManager getComponentManager() {
        return componentManager;
    }

    EntityManager getEntityManager() {
        return entityManager;
    }
}
