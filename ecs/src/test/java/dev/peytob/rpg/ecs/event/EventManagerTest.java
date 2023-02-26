package dev.peytob.rpg.ecs.event;

import dev.peytob.rpg.ecs.EcsTests;
import dev.peytob.rpg.ecs.event.implementation.event.FirstEvent;
import dev.peytob.rpg.ecs.event.implementation.event.SecondEvent;
import dev.peytob.rpg.ecs.event.implementation.event.ThirdEvent;
import dev.peytob.rpg.ecs.event.implementation.handler.FirstEventHandler;
import dev.peytob.rpg.ecs.event.implementation.handler.SecondEventHandler;
import dev.peytob.rpg.ecs.event.implementation.handler.ThirdEventHandler;
import dev.peytob.rpg.ecs.exception.EventHandlerAlreadyRegisteredException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

abstract class EventManagerTest extends EcsTests {

    EventManager eventManager;

    abstract EventManager createInstance();

    @BeforeEach
    void setUpEventManager() {
        this.eventManager = createInstance();
    }

    @Test
    void newEventManagerIsEmpty() {
        assertEquals(0, eventManager.getSize());
    }

    @Test
    void eventHandlerSuccessfullyRegistered() {
        EventHandler<FirstEvent> eventHandler = new FirstEventHandler();

        eventManager.register(eventHandler);

        assertEquals(1, eventManager.getSize());
        assertEquals(Collections.singleton(eventHandler), eventManager.getHandlersFor(FirstEvent.class));
        assertTrue(eventManager.contains(eventHandler));
    }

    @Test
    void eventManagerCanFindEventHandlerByEventClass() {
        EventHandler<FirstEvent> firstEventHandler = new FirstEventHandler();
        EventHandler<SecondEvent> secondEventHandler = new SecondEventHandler();

        eventManager.register(firstEventHandler);
        eventManager.register(secondEventHandler);

        assertEquals(Collections.singleton(firstEventHandler), eventManager.getHandlersFor(FirstEvent.class));
    }

    @Test
    void eventHandlerCantBeRegisteredMoreThanOneTime() {
        EventHandler<FirstEvent> firstEventHandler = new FirstEventHandler();

        assertTrue(eventManager.register(firstEventHandler));
        assertThrows(EventHandlerAlreadyRegisteredException.class, () ->
                eventManager.register(firstEventHandler));
    }

    @Test
    void manySameEventsHandlersCantBeRegistered() {
        EventHandler<FirstEvent> firstEventHandler = new FirstEventHandler();
        EventHandler<FirstEvent> sameFirstEventHandler = new FirstEventHandler();

        assertTrue(eventManager.register(firstEventHandler));
        assertThrows(EventHandlerAlreadyRegisteredException.class, () ->
                eventManager.register(sameFirstEventHandler));
    }

    @Test
    void eventHandlerSuccessfullyRemoved() {
        EventHandler<FirstEvent> firstEventHandler = new FirstEventHandler();
        EventHandler<SecondEvent> secondEventHandler = new SecondEventHandler();
        EventHandler<ThirdEvent> thirdEventHandler = new ThirdEventHandler();

        eventManager.register(firstEventHandler);
        eventManager.register(secondEventHandler);
        eventManager.register(thirdEventHandler);

        eventManager.remove(secondEventHandler);

        assertEquals(Collections.singleton(firstEventHandler), eventManager.getHandlersFor(FirstEvent.class));
        assertEquals(Collections.singleton(thirdEventHandler), eventManager.getHandlersFor(ThirdEvent.class));
    }

    @Test
    void eventManagerIsEmptyAfterClear() {
        EventHandler<FirstEvent> firstEventHandler = new FirstEventHandler();
        EventHandler<SecondEvent> secondEventHandler = new SecondEventHandler();

        eventManager.register(firstEventHandler);
        eventManager.register(secondEventHandler);

        eventManager.clear();

        assertEquals(0, eventManager.getSize());
    }
}