package dev.peytob.rpg.ecs;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class EcsTests {

    protected <E> void assertElementsEquals(Collection<E> expected, Collection<E> actual) {
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual));
    }
}
