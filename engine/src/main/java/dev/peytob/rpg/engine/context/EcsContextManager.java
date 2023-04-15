package dev.peytob.rpg.engine.context;

import dev.peytob.rpg.ecs.context.Contexts;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.ecs.event.Event;
import dev.peytob.rpg.ecs.system.OrderedSystem;
import dev.peytob.rpg.ecs.system.SystemManager;
import dev.peytob.rpg.engine.context.initializer.entity.EngineEntityComponentInitializer;
import dev.peytob.rpg.engine.context.template.EcsContextTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public final class EcsContextManager {

    private static final Logger logger = LoggerFactory.getLogger(EcsContextManager.class);

    private EcsContext ecsContext;

    private final List<EngineEntityComponentInitializer> systemEntitiesComponentsInitializers;

    public EcsContextManager(List<EngineEntityComponentInitializer> systemEntitiesComponentsInitializers) {
        this.systemEntitiesComponentsInitializers = systemEntitiesComponentsInitializers;
        this.ecsContext = createContext();
    }

    public void refreshContext(EcsContextTemplate ecsContextTemplate) {
        logger.info("Refreshing ECS context started");

        logger.info("Clearing current context");
        clearContext();

        logger.info("Injecting context systems in new ECS context");
        injectSystems(ecsContextTemplate.defaultSystems());

        logger.info("Creating and injecting base engine entities");
        injectSystemEntities(systemEntitiesComponentsInitializers);

        logger.info("ECS context has been refreshed");
    }

    public void executeSystems() {
        ecsContext.executeSystems();
        ecsContext.getEventManager().clear();
    }

    public void raiseEvent(Event event) {
        ecsContext.getEventManager().register(event);
    }

    public EcsContext getRawEcsContext() {
        return ecsContext;
    }

    private void injectSystems(Collection<OrderedSystem> systems) {
        SystemManager systemManager = ecsContext.getSystemManager();

        systems.forEach(systemManager::register);
    }

    private void injectSystemEntities(List<EngineEntityComponentInitializer> initializers) {
        initializers.forEach(initializer -> {
            Entity entity = ecsContext.newEntity(initializer.getId());
            initializer.inject(entity);
        });
    }

    private void clearContext() {
        ecsContext.clearEntities();
        ecsContext = createContext();
    }

    private EcsContext createContext() {
        return Contexts.mutable();
    }
}
