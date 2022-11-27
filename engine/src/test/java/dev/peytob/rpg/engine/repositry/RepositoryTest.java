package dev.peytob.rpg.engine.repositry;

import dev.peytob.rpg.engine.NonContextRpgEngineTest;
import dev.peytob.rpg.engine.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class RepositoryTest<R extends Record & Resource> extends NonContextRpgEngineTest {

    Repository<R> repository;

    abstract Repository<R> createInstance();

    abstract R createTestResource(Integer id, String textId);

    @BeforeEach
    void createNewRepository() {
        this.repository = createInstance();
    }

    @Test
    void newRepositoryIsEmpty() {
        assertEquals(0, repository.getCount());
        assertTrue(repository.getAll().isEmpty());
    }

    @Test
    void resourceCanBeCreatedSuccessfully() {
        R resource = createTestResource(1, "b");
        repository.append(resource);

        assertEquals(resource, repository.getById(resource.id()));
        assertEquals(resource, repository.getById(resource.textId()));
        assertEquals(1, repository.getAll().size());
        assertTrue(repository.getAll().contains(resource));
    }

    @Test
    void resourceCanBeFoundedByContainsMethod() {
        R resource = createTestResource(214, "some_text_id");
        repository.append(resource);

        assertTrue(repository.contains(resource.id()));
        assertTrue(repository.contains(resource.textId()));
    }

    @Test
    void containsReturnsFalseInResourceNotExists() {
        R resource = createTestResource(1, "exists");
        repository.append(resource);

        assertFalse(repository.contains(1254));
        assertFalse(repository.contains("not_exists_id"));
    }

    @Test
    void resourceCantBeAppendedTwoTimes() {
        R resource = createTestResource(1, "exists");
        assertTrue(repository.append(resource));
        assertFalse(repository.append(resource));

        assertEquals(1, repository.getCount());
        assertEquals(1, repository.getAll().size());
    }

    @Test
    void resourceRemovedSuccessfully() {
        R resource1 = createTestResource(1, "first");
        R resource2 = createTestResource(2, "second");

        assertTrue(repository.append(resource1));
        assertTrue(repository.append(resource2));

        assertEquals(2, repository.getCount());

        assertTrue(repository.remove(resource2));

        assertEquals(1, repository.getCount());
        assertFalse(repository.contains(resource2.id()));
        assertFalse(repository.contains(resource2.textId()));
        assertFalse(repository.getAll().contains(resource2));
    }

    @Test
    void removeReturnsFalseIfResourceNotPresent() {
        assertFalse(repository.remove(createTestResource(1231, "some_res")));
    }
}