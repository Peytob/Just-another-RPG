package dev.peytob.rpg.engine.context;

import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.context.EcsContexts;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.ecs.event.Event;
import dev.peytob.rpg.ecs.system.OrderedSystem;
import dev.peytob.rpg.engine.context.initializer.entity.EngineEntityComponentInitializer;
import dev.peytob.rpg.engine.context.template.EcsContextTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public final class EcsContextManager {

    private static final Logger logger = LoggerFactory.getLogger(EcsContextManager.class);

    private EcsContext ecsContext;

    private final List<EngineEntityComponentInitializer> systemEntitiesComponentsInitializers;

    public EcsContextManager(List<EngineEntityComponentInitializer> systemEntitiesComponentsInitializers) {
        this.systemEntitiesComponentsInitializers = systemEntitiesComponentsInitializers;
        this.ecsContext = createContext(Collections.emptyList());
    }

    public void refreshContext(EcsContextTemplate ecsContextTemplate) {
        logger.info("Refreshing ECS context started");

        logger.info("Clearing current context");
        clearContextEntities();

        logger.info("Creating new ECS context");
        ecsContext = createContext(ecsContextTemplate.defaultSystems());

        logger.info("Creating and injecting base engine entities");
        injectSystemEntities(systemEntitiesComponentsInitializers);

        logger.info("ECS context has been refreshed");
    }

    public void executeSystems() {
        ecsContext.getSystems().forEach(system -> system.execute(ecsContext));
        ecsContext.clearEvents();
    }

    public void addEvent(Event event) {
        ecsContext.addEvent(event);
    }

    public EcsContext getRawEcsContext() {
        return ecsContext;
    }

    private void injectSystemEntities(List<EngineEntityComponentInitializer> initializers) {
        initializers.forEach(initializer -> {
            Entity entity = ecsContext.createEntity(initializer.getId());
            initializer.inject(entity);
        });
    }

    private void clearContextEntities() {
        List
            .copyOf(ecsContext.getAllEntities())
            .forEach(ecsContext::removeEntity);
    }

    private EcsContext createContext(Collection<OrderedSystem> systems) {
        return EcsContexts.mutable(systems);
    }
}
