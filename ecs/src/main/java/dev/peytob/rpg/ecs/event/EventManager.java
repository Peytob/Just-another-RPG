package dev.peytob.rpg.ecs.event;

import java.util.Collection;

public interface EventManager {

    <T extends Event> Collection<EventHandler<T>> getHandlersFor(Class<T> eventClass);

    boolean register(EventHandler<? extends Event> eventHandler);

    boolean remove(EventHandler<? extends Event> eventHandler);

    boolean contains(EventHandler<? extends Event> eventHandler);

    void clear();

    int getSize();
}
