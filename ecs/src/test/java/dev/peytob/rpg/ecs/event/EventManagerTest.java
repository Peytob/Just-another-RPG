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
                eventManager.getEventTypes()
        );

        assertEquals(0, eventManager.getEventsCount());
    }

    @Test
    void returnsEmptyCollectionAtNotExistsType() {
        assertIterableEquals(
                Collections.emptyList(),
                eventManager.getEventsByType(FirstEvent.class)
        );
    }

    @Test
    void oneEventSuccessfullyRegistered() {
        FirstEvent event = new FirstEvent();
        Class<FirstEvent> eventClass = FirstEvent.class;

        eventManager.addEvent(event);

        assertElementsEquals(
                eventManager.getEventTypes(),
                Collections.singleton(eventClass)
        );

        assertElementsEquals(
                eventManager.getEventsByType(eventClass),
                Collections.singleton(event)
        );
    }

    @Test
    void manyEventsWithSingleTypeSuccessfullyRegistered() {
        FirstEvent event1 = new FirstEvent();
        FirstEvent event2 = new FirstEvent();
        FirstEvent event3 = new FirstEvent();
        Class<FirstEvent> eventClass = FirstEvent.class;

        eventManager.addEvent(event1);
        eventManager.addEvent(event2);
        eventManager.addEvent(event3);

        assertEquals(3, eventManager.getEventsCount());

        assertElementsEquals(
                Collections.singleton(eventClass),
                eventManager.getEventTypes()
        );

        assertElementsEquals(
                List.of(event1, event2, event3),
                eventManager.getEventsByType(eventClass)
        );
    }

    @Test
    void manyEventsWithDifferentTypesSuccessfullyRegistered() {
        FirstEvent firstEvent = new FirstEvent();
        SecondEvent secondEvent = new SecondEvent();

        eventManager.addEvent(firstEvent);
        eventManager.addEvent(secondEvent);

        assertEquals(2, eventManager.getEventsCount());

        assertElementsEquals(
                List.of(firstEvent.getClass(), secondEvent.getClass()),
                eventManager.getEventTypes()
        );

        assertElementsEquals(
                Collections.singleton(firstEvent),
                eventManager.getEventsByType(FirstEvent.class)
        );

        assertElementsEquals(
                Collections.singleton(secondEvent),
                eventManager.getEventsByType(SecondEvent.class)
        );
    }

    @Test
    void typeDisappearsAfterRemovingLastTypeEvent() {
        Event firstEvent = new FirstEvent();
        Event secondEvent = new SecondEvent();

        eventManager.addEvent(firstEvent);
        eventManager.addEvent(secondEvent);

        eventManager.removeEvent(firstEvent);

        assertElementsEquals(
                Collections.singleton(secondEvent.getClass()),
                eventManager.getEventTypes()
        );

        assertIterableEquals(
                Collections.emptyList(),
                eventManager.getEventsByType(firstEvent.getClass())
        );

        assertEquals(1, eventManager.getEventsCount());
    }

    @Test
    void eventManagerDeletesAllElementsAfterClearing() {
        Event firstEvent = new FirstEvent();
        Event secondEvent = new SecondEvent();

        eventManager.addEvent(firstEvent);
        eventManager.addEvent(secondEvent);

        eventManager.clear();

        assertElementsEquals(
                Collections.emptyList(),
                eventManager.getEventTypes()
        );

        assertEquals(0, eventManager.getEventsCount());
    }

    @Test
    void typesCollectionShouldBeImmutable() {
        Event event = new FirstEvent();
        eventManager.addEvent(event);

        Collection<Class<? extends Event>> types = eventManager.getEventTypes();

        assertThrows(UnsupportedOperationException.class, () -> types.add(Event.class));
        assertThrows(UnsupportedOperationException.class, types::clear);
        assertThrows(UnsupportedOperationException.class, () -> types.remove(event.getClass()));
    }

    @Test
    void eventsCollectionShouldBeImmutable() {
        FirstEvent event = new FirstEvent();
        eventManager.addEvent(event);

        Collection<FirstEvent> events = eventManager.getEventsByType(FirstEvent.class);

        assertThrows(UnsupportedOperationException.class, () -> events.add(new FirstEvent()));
        assertThrows(UnsupportedOperationException.class, events::clear);
        assertThrows(UnsupportedOperationException.class, () -> events.remove(event));
    }
}