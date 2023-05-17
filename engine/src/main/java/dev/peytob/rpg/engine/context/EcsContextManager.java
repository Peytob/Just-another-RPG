package dev.peytob.rpg.engine.context;

import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.context.EcsContextBuilder;
import dev.peytob.rpg.ecs.context.EcsContexts;
import dev.peytob.rpg.ecs.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class EcsContextManager {

    private static final Logger logger = LoggerFactory.getLogger(EcsContextManager.class);

    private EcsContext ecsContext;

    public EcsContextManager() {
        this.ecsContext = EcsContexts.empty();
    }

    public void refreshContext(EcsContextBuilder ecsContextBuilder) {
        logger.info("Refreshing ECS context started");

        logger.info("Clearing current context");
        clearContextEntities();

        logger.info("Creating new ECS context");
        ecsContext = ecsContextBuilder.build();

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

    private void clearContextEntities() {
        List
            .copyOf(ecsContext.getAllEntities())
            .forEach(ecsContext::removeEntity);
    }
}
