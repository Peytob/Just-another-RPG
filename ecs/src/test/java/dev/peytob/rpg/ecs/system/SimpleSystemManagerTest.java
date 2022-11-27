package dev.peytob.rpg.ecs.system;

public class SimpleSystemManagerTest extends SystemManagerTest {

    @Override
    SystemManager createInstance() {
        return new SimpleSystemManager();
    }
}
