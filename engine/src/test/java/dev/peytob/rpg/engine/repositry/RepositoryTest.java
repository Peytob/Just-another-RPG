package dev.peytob.rpg.engine.repositry;

import dev.peytob.rpg.engine.NonContextRpgEngineTest;
import dev.peytob.rpg.engine.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class RepositoryTest<R extends Record & Resource> extends NonContextRpgEngineTest {

    Repository<R> repository;

    abstract Repository<R> createInstance();

    abstract R createTestResource(String textId);

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
        R resource = createTestResource("b");
        repository.append(resource);

        Optional<R> resourceOptional = repository.getById(resource.id());

        assertTrue(resourceOptional.isPresent());
        assertEquals(resource, resourceOptional.get());
        assertEquals(1, repository.getAll().size());
        assertTrue(repository.getAll().contains(resource));
    }

    @Test
    void resourceCanBeFoundedByContainsMethod() {
        R resource = createTestResource("some_text_id");
        repository.append(resource);

        assertTrue(repository.contains(resource.id()));
    }

    @Test
    void containsReturnsFalseInResourceNotExists() {
        R resource = createTestResource("exists");
        repository.append(resource);

        assertTrue(repository.contains(resource.id()));
        assertFalse(repository.contains("not_exists_id"));
    }

    @Test
    void resourceCantBeAppendedTwoTimes() {
        R resource = createTestResource("exists");
        assertTrue(repository.append(resource));
        assertFalse(repository.append(resource));

        assertEquals(1, repository.getCount());
        assertEquals(1, repository.getAll().size());
    }

    @Test
    void resourceRemovedSuccessfully() {
        R resource1 = createTestResource("first");
        R resource2 = createTestResource("second");

        assertTrue(repository.append(resource1));
        assertTrue(repository.append(resource2));

        assertEquals(2, repository.getCount());

        assertTrue(repository.remove(resource2));

        assertEquals(1, repository.getCount());
        assertFalse(repository.contains(resource2.id()));
        assertFalse(repository.getAll().contains(resource2));
    }

    @Test
    void removeReturnsFalseIfResourceNotPresent() {
        assertFalse(repository.remove(createTestResource("some_res")));
    }
}