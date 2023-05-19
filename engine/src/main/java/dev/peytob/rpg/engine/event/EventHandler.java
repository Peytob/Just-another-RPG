package dev.peytob.rpg.engine.event;

public interface EventHandler<T> {

    void handleEvent(T event);
}
