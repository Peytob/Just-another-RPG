package dev.peytob.rpg.ecs.context;

import dev.peytob.rpg.ecs.component.ComponentManager;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.ecs.entity.EntityManager;
import dev.peytob.rpg.ecs.entity.filer.FilterManager;
import dev.peytob.rpg.ecs.event.EventManager;
import dev.peytob.rpg.ecs.system.SystemManager;

public interface EcsContext {

    ComponentManager getUnmodifiableComponentManager();

    EntityManager getUnmodifiableEntityManager();

    SystemManager getSystemManager();

    EventManager getEventManager();

    FilterManager getFilterManager();

    Entity newEntity(String id);

    boolean removeEntity(Entity entity);

    void clearEntities();

    void executeSystems();
}
