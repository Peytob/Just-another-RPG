package dev.peytob.rpg.engine.context;

import dev.peytob.rpg.ecs.system.OrderedSystem;
import dev.peytob.rpg.ecs.system.System;
import dev.peytob.rpg.engine.ContextRpgEngineTest;
import dev.peytob.rpg.engine.context.template.EcsContextTemplate;
import dev.peytob.rpg.engine.system.FirstTestSystem;
import dev.peytob.rpg.engine.system.SecondTestSystem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EcsContextManagerTest extends ContextRpgEngineTest {

    @Autowired
    EcsContextManager contextManager;

    @Test
    void contextManagerInjectsSystemsAfterRefreshing() {
        EcsContextTemplate ecsContextTemplate = new EcsContextTemplate(
            List.of(
                OrderedSystem.wrap(new FirstTestSystem(), 1),
                OrderedSystem.wrap(new SecondTestSystem(), 2)
            )
        );

        contextManager.refreshContext(ecsContextTemplate);
        List<? extends Class<? extends System>> systems = contextManager.getRawEcsContext().getSystems()
            .stream()
            .map(System::getClass)
            .toList();

        boolean isAllSystemsRegisteredInSystemManager = ecsContextTemplate.defaultSystems().stream()
            .sorted(Comparator.comparingInt(OrderedSystem::getOrder))
            .map(OrderedSystem::getSystem)
            .map(System::getClass)
            .allMatch(systems::contains);
        assertTrue(isAllSystemsRegisteredInSystemManager);
    }
}
