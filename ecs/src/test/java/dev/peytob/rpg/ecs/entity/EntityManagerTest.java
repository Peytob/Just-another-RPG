package dev.peytob.rpg.ecs.entity;

import dev.peytob.rpg.ecs.EcsTests;
import dev.peytob.rpg.ecs.exception.EntityAlreadyRegisteredException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class EntityManagerTest extends EcsTests {

    EntityManager entityManager;

    abstract EntityManager createInstance();

    @BeforeEach
    void setUpComponentManagerInstance() {
        this.entityManager = createInstance();
    }

    @Test
    void newEntityManagerIsEmpty() {
        assertTrue(entityManager.getAll().isEmpty());
    }

    @Test
    void entitySuccessfullyRegistered() {
        Entity entity = createEntity("someEntity");
        assertDoesNotThrow(() -> entityManager.register(entity));
        assertEquals(1, entityManager.getSize());
    }

    @Test
    void entitySuccessfullyRemoved() {
        Entity first = createEntity("a");
        Entity second = createEntity("b");
        Entity third = createEntity("c");

        entityManager.register(first);
        entityManager.register(second);
        entityManager.register(third);

        entityManager.remove(second);

        assertElementsEquals(
                List.of(first, third),
                entityManager.getAll()
        );
    }

    @Test
    void entityManagerIsEmptyAfterClear() {
        entityManager.register(createEntity("a"));
        entityManager.register(createEntity("b"));
        entityManager.register(createEntity("c"));

        assertEquals(3, entityManager.getSize());

        entityManager.clear();

        assertTrue(entityManager.getAll().isEmpty());
        assertEquals(0, entityManager.getSize());
    }

    @Test
    void throwsExceptionIfEntityWithSomeIdExists() {
        final String id = "someid";
        Entity entity = createEntity(id);
        Entity otherEntityWithSomeId = createEntity(id);

        entityManager.register(entity);
        assertThrows(EntityAlreadyRegisteredException.class, () -> entityManager.register(otherEntityWithSomeId));
    }

    @Test
    void entityCantBeRegisteredTwoTimes() {
        Entity entity = createEntity("sdfa");

        entityManager.register(entity);
        assertThrows(EntityAlreadyRegisteredException.class, () -> entityManager.register(entity));
    }

    private Entity createEntity(String id) {
        Entity mock = Mockito.mock(Entity.class, "Entity-" + id);
        Mockito.when(mock.getId()).thenReturn(id);
        return mock;
    }
}