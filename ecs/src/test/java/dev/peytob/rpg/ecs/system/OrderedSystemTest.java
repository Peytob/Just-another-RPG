package dev.peytob.rpg.ecs.system;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderedSystemTest {

    private static final System testSystem = ctx -> {};

    @Test
    void orderedSystemSuccessfullyCreated() {
        int order = 42;

        OrderedSystem orderedSystem = OrderedSystem.wrap(testSystem, order);

        assertEquals(order, orderedSystem.getOrder());
        assertEquals(testSystem, orderedSystem.getSystem());
    }

    @Test
    void wrappingOrderedSystemWithEqualsOrderReturnsSameWrapper() {
        int order = 10;

        OrderedSystem orderedSystem = OrderedSystem.wrap(testSystem, order);
        OrderedSystem reorderedSystem = OrderedSystem.wrap(orderedSystem, order);

        assertSame(orderedSystem, reorderedSystem);
    }

    @Test
    void wrappingOrderedSystemWithDifferentOrdersReturnsOtherWrapper() {
        int newOrder = -47;

        OrderedSystem orderedSystem = OrderedSystem.wrap(testSystem, 12);
        OrderedSystem reorderedSystem = OrderedSystem.wrap(orderedSystem, newOrder);

        assertEquals(testSystem, reorderedSystem.getSystem());
        assertEquals(newOrder, reorderedSystem.getOrder());
    }
}