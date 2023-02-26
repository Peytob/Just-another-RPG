package dev.peytob.rpg.ecs.event;

public enum EventManagers {;

    public static EventManager mutable() {
        return new SimpleEventManager();
    }
}
