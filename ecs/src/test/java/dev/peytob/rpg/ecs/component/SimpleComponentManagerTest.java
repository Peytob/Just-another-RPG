package dev.peytob.rpg.ecs.component;

public class SimpleComponentManagerTest extends ComponentManagerTest {

    @Override
    ComponentManager createInstance() {
        return new SimpleComponentManager();
    }
}
