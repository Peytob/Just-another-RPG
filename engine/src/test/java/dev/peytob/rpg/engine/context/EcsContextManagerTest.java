package dev.peytob.rpg.engine.context;

import dev.peytob.rpg.ecs.system.OrderedSystem;
import dev.peytob.rpg.ecs.system.System;
import dev.peytob.rpg.engine.ContextRpgEngineTest;
import dev.peytob.rpg.engine.context.template.EcsContextTemplate;
import dev.peytob.rpg.engine.context.template.TestEcsContextTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EcsContextManagerTest extends ContextRpgEngineTest {

    @Autowired
    private EcsContextManager contextManager;

    @Test
    void contextManagerInjectsSystemsAfterRefreshing() {
        EcsContextTemplate ecsContextTemplate = new TestEcsContextTemplate();

        contextManager.refreshContext(ecsContextTemplate);
        List<? extends Class<? extends System>> systems = contextManager.getEcsContext().getSystems()
            .stream()
            .map(System::getClass)
            .toList();

        boolean isAllSystemsRegisteredInSystemManager = ecsContextTemplate.getDefaultContextSystems().stream()
            .sorted(Comparator.comparingInt(OrderedSystem::getOrder))
            .map(OrderedSystem::getSystem)
            .map(System::getClass)
            .allMatch(systems::contains);

        assertTrue(isAllSystemsRegisteredInSystemManager);
    }
}
