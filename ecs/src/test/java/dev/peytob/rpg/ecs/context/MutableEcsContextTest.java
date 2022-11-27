package dev.peytob.rpg.ecs.context;

public class MutableEcsContextTest extends EcsContextTest {

    @Override
    EcsContext createInstance() {
        return Contexts.mutable();
    }
}
