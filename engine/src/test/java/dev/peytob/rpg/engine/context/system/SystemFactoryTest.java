package dev.peytob.rpg.engine.context.system;

import dev.peytob.rpg.ecs.system.System;
import dev.peytob.rpg.engine.ContextRpgEngineTest;
import dev.peytob.rpg.engine.exception.system.SystemNotFound;
import dev.peytob.rpg.engine.system.FirstTestSystem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SystemFactoryTest extends ContextRpgEngineTest {

    @Autowired
    SystemFactory systemFactory;

    @Autowired
    FirstTestSystem firstSystem;

    @Autowired
    List<System> allContextSystems;

    @Test
    void systemFactoryContainsAllContextSystems() {
        assertEquals(allContextSystems.size(), systemFactory.getSystemsCount());
    }

    @Test
    void returnsSystemByClass() {
        final Class<? extends System> systemClass = firstSystem.getClass();

        System foundedSystem = systemFactory.getSystem(systemClass);

        assertEquals(firstSystem, foundedSystem);
    }

    @Test
    void throwsSystemNotFoundIfSystemNotExists() {
        final System unknownSystem = ctx -> {};

        assertThrows(SystemNotFound.class, () -> systemFactory.getSystem(unknownSystem.getClass()));
    }
}