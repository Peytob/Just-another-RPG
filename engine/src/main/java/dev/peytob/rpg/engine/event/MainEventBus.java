package dev.peytob.rpg.engine.event;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static dev.peytob.rpg.engine.utils.reflection.GenericReflectionUtils.resolveTypeArgument;

@Component
public class MainEventBus implements EventBus {

    private final Multimap<Class<?>, EventHandler<?>> eventHandlers;

    public MainEventBus(Collection<EventHandler<?>> eventHandlers) {
        this.eventHandlers = HashMultimap.create();
        eventHandlers
            .forEach(handler -> this.eventHandlers.put(resolveTypeArgument(handler.getClass(), EventHandler.class), handler));
    }

    @Override
    public void addEvent(Object event) {
        getEventHandlers(event.getClass())
            .forEach(eventHandler -> handleEvent(eventHandler, event));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private <T> Collection<EventHandler<T>> getEventHandlers(Class<T> eventClass) {
        Collection concreteEventHandlers = eventHandlers.get(eventClass);
        return (Collection<EventHandler<T>>) concreteEventHandlers;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void handleEvent(EventHandler eventHandler, Object event) {
        eventHandler.handleEvent(event);
    }
}
