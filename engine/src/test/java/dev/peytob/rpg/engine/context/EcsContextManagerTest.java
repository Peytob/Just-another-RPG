package dev.peytob.rpg.engine.context;

import dev.peytob.rpg.ecs.system.OrderedSystem;
import dev.peytob.rpg.ecs.system.System;
import dev.peytob.rpg.ecs.system.SystemManager;
import dev.peytob.rpg.engine.NonContextRpgEngineTest;
import dev.peytob.rpg.engine.context.template.EcsContextTemplate;
import dev.peytob.rpg.engine.context.template.TestEcsContextTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EcsContextManagerTest extends NonContextRpgEngineTest {

    private EcsContextManager contextManager;

    @BeforeEach
    void setUpContextManager() {
        this.contextManager = new EcsContextManager();
    }

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
