package dev.peytob.rpg.ecs.context;

import dev.peytob.rpg.ecs.component.Component;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.ecs.event.Event;
import dev.peytob.rpg.ecs.system.System;

import java.util.Collection;
import java.util.Optional;

public interface EcsContext {

    Optional<Entity> getEntityById(String entityId);

    Collection<Entity> getAllEntities();

    boolean removeEntity(Entity entity);

    Entity createEntity(String entityId);

    Entity getComponentEntity(Component component);

    <T extends Component> Collection<T> getComponentsByType(Class<T> componentType);

    <T extends Component> Optional<T> getSingletonComponentByType(Class<T> componentType);

    Collection<Class<? extends Component>> getComponentTypes();

    Collection<System> getSystems();

    void addEvent(Event event);

    <T extends Event> Collection<T> getEventsByType(Class<T> event);

    Collection<Class<? extends Event>> getEventTypes();

    void clearEvents();

    boolean removeEvent(Event event);
}
