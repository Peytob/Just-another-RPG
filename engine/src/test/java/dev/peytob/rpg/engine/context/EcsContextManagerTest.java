package dev.peytob.rpg.engine.context;

import dev.peytob.rpg.ecs.system.OrderedSystem;
import dev.peytob.rpg.ecs.system.System;
import dev.peytob.rpg.ecs.system.SystemManager;
import dev.peytob.rpg.engine.ContextRpgEngineTest;
import dev.peytob.rpg.engine.context.template.EcsContextTemplate;
import dev.peytob.rpg.engine.context.template.TestEcsContextTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EcsContextManagerTest extends ContextRpgEngineTest {

    @Autowired
    private EcsContextManager contextManager;

    @Test
    void contextManagerInjectsSystemsAfterRefreshing() {
        EcsContextTemplate ecsContextTemplate = new TestEcsContextTemplate();

        contextManager.refreshContext(ecsContextTemplate);
        SystemManager systemManager = contextManager.getEcsContext().getSystemManager();

        boolean isAllSystemsRegisteredInSystemManager = ecsContextTemplate.getDefaultContextSystems().stream()
                .map(OrderedSystem::getSystem)
                .map(System::getClass)
                .allMatch(systemManager::contains);
        assertTrue(isAllSystemsRegisteredInSystemManager);
    }
}
