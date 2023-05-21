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
        assertTrue(systemManager.getAllSystems().isEmpty());
    }

    @Test
    void systemSuccessfullyRegistered() {
        System system = ctx -> {};
        systemManager.registerSystem(system, 1);

        assertElementsEquals(Collections.singleton(system), systemManager.getAllSystems());
        assertTrue(systemManager.containsSystem(system));
    }

    @Test
    void systemManagerCanFindSystemByClass() {
        System firstSystem = ctx -> {};
        System secondSystem = ctx -> {};

        systemManager.registerSystem(firstSystem, 1);
        systemManager.registerSystem(secondSystem, 2);

        systemManager.containsSystem(secondSystem.getClass());
    }

    @Test
    void systemManagerCanFindSystemByObject() {
        System firstSystem = ctx -> {};
        System secondSystem = ctx -> {};

        systemManager.registerSystem(firstSystem, 1);
        systemManager.registerSystem(secondSystem, 2);

        systemManager.containsSystem(firstSystem);
    }

    @Test
    void systemSuccessfullyRemoved() {
        System firstSystem = ctx -> {};
        System secondSystem = ctx -> {};
        System thirdSystem = ctx -> {};

        systemManager.registerSystem(firstSystem, 1);
        systemManager.registerSystem(secondSystem, 1);
        systemManager.registerSystem(thirdSystem, 1);

        assertDoesNotThrow(() -> systemManager.removeSystem(secondSystem));

        assertElementsEquals(List.of(firstSystem, thirdSystem), systemManager.getAllSystems());
    }

    @Test
    void systemManagerIsEmptyAfterClear() {
        systemManager.registerSystem(ctx -> {}, 1);
        systemManager.registerSystem(ctx -> {}, 2);
        systemManager.registerSystem(ctx -> {}, 3);

        systemManager.clear();

        assertTrue(systemManager.getAllSystems().isEmpty());
    }

    @Test
    void throwsExceptionIfSystemWithSomeClassAlreadyRegistered() {
        System firstSystem = new TestSystem();
        System secondSystem = new TestSystem();
        System thirdSystem = new TestSystem();

        systemManager.registerSystem(firstSystem, 1);
        assertThrows(SystemAlreadyRegisteredException.class, () -> systemManager.registerSystem(secondSystem, 1));
        assertThrows(SystemAlreadyRegisteredException.class, () -> systemManager.registerSystem(thirdSystem, 5));
    }

    @Test
    void systemStoredInRightOrder() {
        System firstSystem = ctx -> {};
        System secondSystem = ctx -> {};
        System thirdSystem = ctx -> {};

        systemManager.registerSystem(secondSystem, 3);
        systemManager.registerSystem(firstSystem, 1);
        systemManager.registerSystem(thirdSystem, 12);

        assertElementsEquals(List.of(firstSystem, secondSystem, thirdSystem), systemManager.getAllSystems());
    }

    @Test
    void sameSystemCantBeRegisteredByDifferentWays() {
        System system = new TestSystem();

        systemManager.registerSystem(system, 1);
        assertThrows(SystemAlreadyRegisteredException.class, () -> systemManager.registerSystem(OrderedSystem.wrap(system, 4)));
    }

    @Test
    void throwsExceptionIfSystemObjectAlreadyRegistered() {
        System system = ctx -> {};
        systemManager.registerSystem(system, 1);
        assertThrows(SystemAlreadyRegisteredException.class, () -> systemManager.registerSystem(system, 3));
    }
}