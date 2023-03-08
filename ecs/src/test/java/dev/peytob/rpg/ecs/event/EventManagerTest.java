package dev.peytob.rpg.ecs.event;

import dev.peytob.rpg.ecs.EcsTests;
import dev.peytob.rpg.ecs.event.implementation.event.FirstEvent;
import dev.peytob.rpg.ecs.event.implementation.event.SecondEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class EventManagerTest extends EcsTests {

    EventManager eventManager;

    abstract EventManager createInstance();

    @BeforeEach
    void createEventManagerInstance() {
        eventManager = createInstance();
    }

    @Test
    void eventManagerIsClearAfterCreating() {
        assertElementsEquals(
                Collections.emptyList(),
                eventManager.getTypes()
        );

        assertEquals(0, eventManager.getSize());
    }

    @Test
    void returnsEmptyCollectionAtNotExistsType() {
        assertIterableEquals(
                Collections.emptyList(),
                eventManager.getAllByType(FirstEvent.class)
        );
    }

    @Test
    void oneEventSuccessfullyRegistered() {
        FirstEvent event = new FirstEvent();
        Class<FirstEvent> eventClass = FirstEvent.class;

        eventManager.register(event);

        assertElementsEquals(
                eventManager.getTypes(),
                Collections.singleton(eventClass)
        );

        assertElementsEquals(
                eventManager.getAllByType(eventClass),
                Collections.singleton(event)
        );
    }

    @Test
    void manyEventsWithSingleTypeSuccessfullyRegistered() {
        FirstEvent event1 = new FirstEvent();
        FirstEvent event2 = new FirstEvent();
        FirstEvent event3 = new FirstEvent();
        Class<FirstEvent> eventClass = FirstEvent.class;

        eventManager.register(event1);
        eventManager.register(event2);
        eventManager.register(event3);

        assertEquals(3, eventManager.getSize());

        assertElementsEquals(
                Collections.singleton(eventClass),
                eventManager.getTypes()
        );

        assertElementsEquals(
                List.of(event1, event2, event3),
                eventManager.getAllByType(eventClass)
        );
    }

    @Test
    void manyEventsWithDifferentTypesSuccessfullyRegistered() {
        FirstEvent firstEvent = new FirstEvent();
        SecondEvent secondEvent = new SecondEvent();

        eventManager.register(firstEvent);
        eventManager.register(secondEvent);

        assertEquals(2, eventManager.getSize());

        assertElementsEquals(
                List.of(firstEvent.getClass(), secondEvent.getClass()),
                eventManager.getTypes()
        );

        assertElementsEquals(
                Collections.singleton(firstEvent),
                eventManager.getAllByType(FirstEvent.class)
        );

        assertElementsEquals(
                Collections.singleton(secondEvent),
                eventManager.getAllByType(SecondEvent.class)
        );
    }

    @Test
    void typeDisappearsAfterRemovingLastTypeEvent() {
        Event firstEvent = new FirstEvent();
        Event secondEvent = new SecondEvent();

        eventManager.register(firstEvent);
        eventManager.register(secondEvent);

        eventManager.remove(firstEvent);

        assertElementsEquals(
                Collections.singleton(secondEvent.getClass()),
                eventManager.getTypes()
        );

        assertIterableEquals(
                Collections.emptyList(),
                eventManager.getAllByType(firstEvent.getClass())
        );

        assertEquals(1, eventManager.getSize());
    }

    @Test
    void eventManagerDeletesAllElementsAfterClearing() {
        Event firstEvent = new FirstEvent();
        Event secondEvent = new SecondEvent();

        eventManager.register(firstEvent);
        eventManager.register(secondEvent);

        eventManager.clear();

        assertElementsEquals(
                Collections.emptyList(),
                eventManager.getTypes()
        );

        assertEquals(0, eventManager.getSize());
    }

    @Test
    void typesCollectionShouldBeImmutable() {
        Event event = new FirstEvent();
        eventManager.register(event);

        Collection<Class<? extends Event>> types = eventManager.getTypes();

        assertThrows(UnsupportedOperationException.class, () -> types.add(Event.class));
        assertThrows(UnsupportedOperationException.class, types::clear);
        assertThrows(UnsupportedOperationException.class, () -> types.remove(event.getClass()));
    }

    @Test
    void eventsCollectionShouldBeImmutable() {
        FirstEvent event = new FirstEvent();
        eventManager.register(event);

        Collection<FirstEvent> events = eventManager.getAllByType(FirstEvent.class);

        assertThrows(UnsupportedOperationException.class, () -> events.add(new FirstEvent()));
        assertThrows(UnsupportedOperationException.class, events::clear);
        assertThrows(UnsupportedOperationException.class, () -> events.remove(event));
    }
}