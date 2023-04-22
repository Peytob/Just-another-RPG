package dev.peytob.rpg.ecs.context;

import dev.peytob.rpg.ecs.component.Component;
import dev.peytob.rpg.ecs.component.ComponentManager;
import dev.peytob.rpg.ecs.component.ComponentManagers;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.ecs.entity.EntityManager;
import dev.peytob.rpg.ecs.entity.EntityManagers;
import dev.peytob.rpg.ecs.entity.GenericEntity;
import dev.peytob.rpg.ecs.event.Event;
import dev.peytob.rpg.ecs.event.EventManager;
import dev.peytob.rpg.ecs.event.EventManagers;
import dev.peytob.rpg.ecs.system.OrderedSystem;
import dev.peytob.rpg.ecs.system.System;
import dev.peytob.rpg.ecs.system.SystemManager;
import dev.peytob.rpg.ecs.system.SystemManagers;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class SimpleEcsContext implements EcsContext {

    private final EntityManager entityManager;

    private final ComponentManager componentManager;

    private final SystemManager systemManager;

    private final EventManager eventManager;

    // TODO Make separated module
    private final Map<Component, Entity> componentToEntityMap;

    SimpleEcsContext(Collection<OrderedSystem> systems) {
        this.entityManager = EntityManagers.mutable();
        this.componentManager = ComponentManagers.mutable();
        this.systemManager = SystemManagers.mutable();
        this.eventManager = EventManagers.mutable();
        this.componentToEntityMap = new ConcurrentHashMap<>();

        systems.forEach(systemManager::registerSystem);
    }

    @Override
    public Optional<Entity> getEntityById(String entityId) {
        return Optional.ofNullable(entityManager.getEntityById(entityId));
    }

    @Override
    public Collection<Entity> getAllEntities() {
        return entityManager.getAllEntities();
    }

    @Override
    public boolean removeEntity(Entity entity) {
        // Maybe it can be optimized
        List
            .copyOf(entity.getComponents())
            .stream()
            .map(Component::getClass)
            .forEach(entity::removeComponent);

        return entityManager.removeEntity(entity);
    }

    @Override
    public Entity createEntity(String entityId) {
        Entity entity = new GenericEntity(entityId);
        Entity contextEntity = new ContextEntity(entity);
        entityManager.addEntity(contextEntity);
        return contextEntity;
    }

    @Override
    public Entity getComponentEntity(Component component) {
        return componentToEntityMap.get(component);
    }

    @Override
    public <T extends Component> Collection<T> getComponentsByType(Class<T> componentType) {
        return componentManager.getComponentsByType(componentType);
    }

    @Override
    public Collection<Class<? extends Component>> getComponentTypes() {
        return componentManager.getComponentTypes();
    }

    @Override
    public Collection<System> getSystems() {
        return systemManager.getAllSystems();
    }

    @Override
    public void addEvent(Event event) {
        eventManager.addEvent(event);
    }

    @Override
    public <T extends Event> Collection<T> getEventsByType(Class<T> event) {
        return eventManager.getEventsByType(event);
    }

    @Override
    public Collection<Class<? extends Event>> getEventTypes() {
        return eventManager.getEventTypes();
    }

    @Override
    public void clearEvents() {
        eventManager.clear();
    }

    @Override
    public boolean removeEvent(Event event) {
        return eventManager.removeEvent(event);
    }

    protected void bindEntityComponent(Component component, Entity entity) {
        componentToEntityMap.put(component, entity);
        componentManager.addComponent(component);
    }

    protected void removeEntityComponent(Component component, Entity entity) {
        componentToEntityMap.remove(component);
        componentManager.removeComponent(component);
    }

    final class ContextEntity implements Entity {

        private final Entity entity;

        ContextEntity(Entity entity) {
            this.entity = entity;
        }

        @Override
        public String getId() {
            return entity.getId();
        }

        @Override
        public Collection<Component> getComponents() {
            return entity.getComponents();
        }

        @Override
        public Collection<Class<? extends Component>> getComponentsTypes() {
            return entity.getComponentsTypes();
        }

        @Override
        public <T extends Component> T getComponent(Class<T> componentClass) {
            return entity.getComponent(componentClass);
        }

        @Override
        public <T extends Component> T removeComponent(Class<T> componentClass) {
            T component = getComponent(componentClass);

            if (component != null) {
                SimpleEcsContext.this.removeEntityComponent(component, this);
            }

            return entity.removeComponent(componentClass);
        }

        @Override
        public void bindComponent(Component component) {
            entity.bindComponent(component);
            SimpleEcsContext.this.bindEntityComponent(component, this);
        }

        @Override
        public boolean isEmpty() {
            return entity.isEmpty();
        }

        @Override
        public boolean isAlive() {
            return SimpleEcsContext.this.getEntityById(getId()).isPresent();
        }
    }
}
