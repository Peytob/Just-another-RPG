package dev.peytob.rpg.ecs.event;

import dev.peytob.rpg.ecs.EcsTests;
import dev.peytob.rpg.ecs.event.implementation.event.FirstEvent;
import dev.peytob.rpg.ecs.event.implementation.handler.FirstEventHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventReflectionUtilsTest extends EcsTests {

    @Test
    void returnsCorrectEventTypeForSimpleHandler() {
        FirstEventHandler firstEventHandler = new FirstEventHandler();
        assertEquals(FirstEvent.class, EventReflectionUtils.resolveHandlerEventType(firstEventHandler));
    }
}