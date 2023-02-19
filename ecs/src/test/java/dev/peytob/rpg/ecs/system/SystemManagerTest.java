package dev.peytob.rpg.ecs.system;

import dev.peytob.rpg.ecs.EcsTests;
import dev.peytob.rpg.ecs.exception.SystemAlreadyRegisteredException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class SystemManagerTest extends EcsTests {

    SystemManager systemManager;

    abstract SystemManager createInstance();

    @BeforeEach
    void setUpSystemManagerInstance() {
        this.systemManager = createInstance();
    }

    @Test
    void newSystemManagerIsEmpty() {
        assertTrue(systemManager.getAll().isEmpty());
    }

    @Test
    void systemSuccessfullyRegistered() {
        System system = ctx -> {};
        systemManager.register(system, 1);

        assertElementsEquals(Collections.singleton(system), systemManager.getAll());
        assertTrue(systemManager.contains(system));
    }

    @Test
    void systemManagerCanFindSystemByClass() {
        System firstSystem = ctx -> {};
        System secondSystem = ctx -> {};

        systemManager.register(firstSystem, 1);
        systemManager.register(secondSystem, 2);

        systemManager.contains(secondSystem.getClass());
    }

    @Test
    void systemManagerCanFindSystemByObject() {
        System firstSystem = ctx -> {};
        System secondSystem = ctx -> {};

        systemManager.register(firstSystem, 1);
        systemManager.register(secondSystem, 2);

        systemManager.contains(firstSystem);
    }

    @Test
    void systemSuccessfullyRemoved() {
        System firstSystem = ctx -> {};
        System secondSystem = ctx -> {};
        System thirdSystem = ctx -> {};

        systemManager.register(firstSystem, 1);
        systemManager.register(secondSystem, 1);
        systemManager.register(thirdSystem, 1);

        assertDoesNotThrow(() -> systemManager.remove(secondSystem));

        assertElementsEquals(List.of(firstSystem, thirdSystem), systemManager.getAll());
    }

    @Test
    void systemManagerIsEmptyAfterClear() {
        systemManager.register(ctx -> {}, 1);
        systemManager.register(ctx -> {}, 2);
        systemManager.register(ctx -> {}, 3);

        systemManager.clear();

        assertTrue(systemManager.getAll().isEmpty());
    }

    @Test
    void throwsExceptionIfSystemWithSomeClassAlreadyRegistered() {
        System firstSystem = new TestSystem();
        System secondSystem = new TestSystem();
        System thirdSystem = new TestSystem();

        systemManager.register(firstSystem, 1);
        assertThrows(SystemAlreadyRegisteredException.class, () -> systemManager.register(secondSystem, 1));
        assertThrows(SystemAlreadyRegisteredException.class, () -> systemManager.register(thirdSystem, 5));
    }

    @Test
    void sameSystemCantBeRegisteredByDifferentWays() {
        System system = new TestSystem();

        systemManager.register(system, 1);
        assertThrows(SystemAlreadyRegisteredException.class, () -> systemManager.register(OrderedSystem.wrap(system, 4)));
    }

    @Test
    void throwsExceptionIfSystemObjectAlreadyRegistered() {
        System system = ctx -> {};
        systemManager.register(system, 1);
        assertThrows(SystemAlreadyRegisteredException.class, () -> systemManager.register(system, 3));
    }
}