package dev.peytob.rpg.ecs.exception;

import dev.peytob.rpg.ecs.event.Event;
import dev.peytob.rpg.ecs.event.EventHandler;

public class EventHandlerAlreadyRegisteredException extends RuntimeException {

    private final EventHandler<? extends Event> eventHandler;

    public EventHandlerAlreadyRegisteredException(String message, EventHandler<? extends Event> eventHandler) {
        super(message);
        this.eventHandler = eventHandler;
    }

    public EventHandler<? extends Event> getEventHandler() {
        return eventHandler;
    }
}
