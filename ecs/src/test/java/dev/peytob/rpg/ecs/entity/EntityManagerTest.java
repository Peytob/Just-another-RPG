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
        assertTrue(entityManager.getAllEntities().isEmpty());
    }

    @Test
    void entitySuccessfullyRegistered() {
        Entity entity = createEntity("someEntity");
        assertDoesNotThrow(() -> entityManager.addEntity(entity));
        assertEquals(1, entityManager.getEntitiesCount());
    }

    @Test
    void entitySuccessfullyRemoved() {
        Entity first = createEntity("a");
        Entity second = createEntity("b");
        Entity third = createEntity("c");

        entityManager.addEntity(first);
        entityManager.addEntity(second);
        entityManager.addEntity(third);

        entityManager.removeEntity(second);

        assertElementsEquals(
                List.of(first, third),
                entityManager.getAllEntities()
        );
    }

    @Test
    void entityManagerIsEmptyAfterClear() {
        entityManager.addEntity(createEntity("a"));
        entityManager.addEntity(createEntity("b"));
        entityManager.addEntity(createEntity("c"));

        assertEquals(3, entityManager.getEntitiesCount());

        entityManager.clear();

        assertTrue(entityManager.getAllEntities().isEmpty());
        assertEquals(0, entityManager.getEntitiesCount());
    }

    @Test
    void throwsExceptionIfEntityWithSomeIdExists() {
        final String id = "someid";
        Entity entity = createEntity(id);
        Entity otherEntityWithSomeId = createEntity(id);

        entityManager.addEntity(entity);
        assertThrows(EntityAlreadyRegisteredException.class, () -> entityManager.addEntity(otherEntityWithSomeId));
    }

    @Test
    void entityCantBeRegisteredTwoTimes() {
        Entity entity = createEntity("sdfa");

        entityManager.addEntity(entity);
        assertThrows(EntityAlreadyRegisteredException.class, () -> entityManager.addEntity(entity));
    }

    private Entity createEntity(String id) {
        Entity mock = Mockito.mock(Entity.class, "Entity-" + id);
        Mockito.when(mock.getId()).thenReturn(id);
        return mock;
    }
}