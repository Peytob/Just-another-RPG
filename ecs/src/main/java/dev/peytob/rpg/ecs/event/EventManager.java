package dev.peytob.rpg.ecs.event;

import java.util.Collection;

public interface EventManager {

    void register(Event event);

    boolean remove(Event event);

    Collection<Class<? extends Event>> getTypes();

    <T extends Event> Collection<T> getAllByType(Class<T> eventClass);

    int getSize();

    void clear();
}
