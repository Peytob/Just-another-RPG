package dev.peytob.rpg.ecs.entity;

import dev.peytob.rpg.ecs.EcsTests;
import dev.peytob.rpg.ecs.component.FirstTestComponent;
import dev.peytob.rpg.ecs.component.SecondTestComponent;
import dev.peytob.rpg.ecs.exception.ComponentAlreadyRegisteredException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class EntityTest extends EcsTests {

    abstract Entity createTestingImplementationEntity();

    Entity entity;

    @BeforeEach
    void setUp() {
        entity = createTestingImplementationEntity();
    }

    @Test
    void isNewEntityIsEmpty() {
        assertTrue(entity.getComponents().isEmpty());
    }

    @Test
    void oneComponentSuccessfullyRegistered() {
        FirstTestComponent testComponent1 = new FirstTestComponent();

        entity.bindComponent(testComponent1);
        Class<? extends FirstTestComponent> componentType = testComponent1.getClass();

        assertEquals(1, entity.getComponents().size());
        assertNotNull(entity.getComponent(componentType));
    }

    @Test
    void multipleComponentsSuccessfullyRegistered() {
        FirstTestComponent testComponent1 = new FirstTestComponent();
        SecondTestComponent testComponent2 = new SecondTestComponent();

        entity.bindComponent(testComponent1);
        entity.bindComponent(testComponent2);

        assertEquals(2, entity.getComponents().size());
        assertNotNull(entity.getComponent(testComponent1.getClass()));
        assertNotNull(entity.getComponent(testComponent2.getClass()));
    }

    @Test
    void entityExceptionThrowsIfDuplicateComponentRegistered() {
        FirstTestComponent testComponent11 = new FirstTestComponent();
        FirstTestComponent testComponent12 = new FirstTestComponent();

        assertDoesNotThrow(() -> entity.bindComponent(testComponent11));
        assertThrows(ComponentAlreadyRegisteredException.class, () -> entity.bindComponent(testComponent12));
    }

    @Test
    void componentSuccessfullyRemoved() {
        FirstTestComponent testComponent1 = new FirstTestComponent();
        SecondTestComponent testComponent2 = new SecondTestComponent();

        entity.bindComponent(testComponent1);
        entity.bindComponent(testComponent2);

        assertEquals(2, entity.getComponents().size());
        assertNotNull(entity.getComponent(testComponent1.getClass()));
        assertNotNull(entity.getComponent(testComponent2.getClass()));

        entity.removeComponent(testComponent1.getClass());

        assertEquals(1, entity.getComponents().size());
        assertNull(entity.getComponent(testComponent1.getClass()));
        assertNotNull(entity.getComponent(testComponent2.getClass()));
    }
}
