package dev.peytob.rpg.engine.context;

import dev.peytob.rpg.ecs.system.OrderedSystem;
import dev.peytob.rpg.ecs.system.System;
import dev.peytob.rpg.ecs.system.SystemManager;
import dev.peytob.rpg.engine.ContextRpgEngineTest;
import dev.peytob.rpg.engine.context.template.EcsContextTemplate;
import dev.peytob.rpg.engine.system.FirstTestSystem;
import dev.peytob.rpg.engine.system.SecondTestSystem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
        SystemManager systemManager = contextManager.getEcsContext().getSystemManager();

        boolean isAllSystemsRegisteredInSystemManager = ecsContextTemplate.defaultSystems().stream()
                .map(OrderedSystem::getSystem)
                .map(System::getClass)
                .allMatch(systemManager::contains);
        assertTrue(isAllSystemsRegisteredInSystemManager);
    }
}
