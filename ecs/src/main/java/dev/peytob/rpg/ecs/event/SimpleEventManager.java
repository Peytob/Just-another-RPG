package dev.peytob.rpg.ecs.event;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.Collections;

public class SimpleEventManager implements EventManager {

    private final Multimap<Class<? extends Event>, Event> events;

    public SimpleEventManager() {
        this.events = HashMultimap.create();
    }

    @Override
    public void register(Event event) {
        Class<? extends Event> eventClass = event.getClass();
        events.put(eventClass, event);
    }

    @Override
    public boolean remove(Event event) {
        Class<? extends Event> eventClass = event.getClass();
        return events.remove(eventClass, event);
    }

    @Override
    public Collection<Class<? extends Event>> getTypes() {
        return Collections.unmodifiableCollection(events.keySet());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Event> Collection<T> getAllByType(Class<T> eventClass) {
        Collection<T> eventsCollection = (Collection<T>) events.get(eventClass);
        return Collections.unmodifiableCollection(eventsCollection);
    }

    @Override
    public int getSize() {
        return events.size();
    }

    @Override
    public void clear() {
        events.clear();
    }
}
