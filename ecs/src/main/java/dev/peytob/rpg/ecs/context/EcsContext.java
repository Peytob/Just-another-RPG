package dev.peytob.rpg.ecs.context;

import dev.peytob.rpg.ecs.component.ComponentManager;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.ecs.entity.EntityManager;
import dev.peytob.rpg.ecs.event.Event;
import dev.peytob.rpg.ecs.event.EventManager;
import dev.peytob.rpg.ecs.system.SystemManager;

public interface EcsContext {

    ComponentManager getUnmodifiableComponentManager();

    EntityManager getUnmodifiableEntityManager();

    SystemManager getSystemManager();

    EventManager getEventManager();

    Entity newEntity(String id);

    boolean removeEntity(Entity entity);

    <T extends Event> void catchEvent(T event);

    void clearEntities();

    void executeSystems();
}
