package dev.peytob.rpg.ecs.event;

public class SimpleEventManagerTest extends EventManagerTest {

    @Override
    EventManager createInstance() {
        return EventManagers.mutable();
    }
}
