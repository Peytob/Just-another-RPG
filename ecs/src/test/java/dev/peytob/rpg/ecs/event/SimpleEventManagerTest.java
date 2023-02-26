package dev.peytob.rpg.ecs.event;

class SimpleEventManagerTest extends EventManagerTest {

    @Override
    EventManager createInstance() {
        return EventManagers.mutable();
    }
}