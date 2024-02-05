package dev.peytob.rpg.ecs.event;

import java.util.Collection;

public interface EventManager {

    void addEvent(Event event);

    boolean removeEvent(Event event);

    Collection<Class<? extends Event>> getEventTypes();

    <T extends Event> Collection<T> getEventsByType(Class<T> eventClass);

    int getEventsCount();

    void clear();

    void removeAllEventsByType(Class<? extends Event> event);
}
