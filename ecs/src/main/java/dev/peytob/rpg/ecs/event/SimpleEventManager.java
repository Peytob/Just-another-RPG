package dev.peytob.rpg.ecs.event;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.peytob.rpg.ecs.exception.EventHandlerAlreadyRegisteredException;

import java.util.Collection;

import static dev.peytob.rpg.ecs.event.EventReflectionUtils.resolveHandlerEventType;

public class SimpleEventManager implements EventManager {

    private final Multimap<Class<? extends Event>, EventHandler<?>> eventHandlers;

    SimpleEventManager() {
        this.eventHandlers = HashMultimap.create();
    }

    @Override
    public Collection<EventHandler<? extends Event>> getHandlersFor(Class<? extends Event> eventClass) {
        return eventHandlers.get(eventClass);
    }

    @Override
    public boolean register(EventHandler<? extends Event> eventHandler) {
        Class<? extends Event> eventType = resolveHandlerEventType(eventHandler);

        if (contains(eventType, eventHandler)) {
            throw new EventHandlerAlreadyRegisteredException("Event handler already registered", eventHandler);
        }

        return eventHandlers.put(eventType, eventHandler);
    }

    @Override
    public boolean remove(EventHandler<? extends Event> eventHandler) {
        Class<? extends Event> eventType = resolveHandlerEventType(eventHandler);

        return eventHandlers.remove(eventType, eventHandler);
    }

    @Override
    public boolean contains(EventHandler<? extends Event> eventHandler) {
        Class<? extends Event> eventType = resolveHandlerEventType(eventHandler);
        return contains(eventType, eventHandler);
    }

    @SuppressWarnings("rawtypes")
    private boolean contains(Class<? extends Event> eventType, EventHandler<? extends Event> eventHandler) {
        Collection<EventHandler<?>> eventHandlers = this.eventHandlers.get(eventType);
        Class<? extends EventHandler> eventHandlerClass = eventHandler.getClass();

        return eventHandlers.contains(eventHandler) ||
                eventHandlers
                .stream()
                .anyMatch(handler -> handler.getClass().equals(eventHandlerClass));
    }

    @Override
    public void clear() {
        eventHandlers.clear();
    }

    @Override
    public int getSize() {
        return eventHandlers.size();
    }
}
