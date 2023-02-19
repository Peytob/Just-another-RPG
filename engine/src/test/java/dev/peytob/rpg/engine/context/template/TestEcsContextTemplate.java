package dev.peytob.rpg.engine.context.template;

import dev.peytob.rpg.ecs.system.OrderedSystem;
import dev.peytob.rpg.engine.system.FirstTestSystem;
import dev.peytob.rpg.engine.system.SecondTestSystem;

import java.util.Collection;
import java.util.List;

public class TestEcsContextTemplate implements EcsContextTemplate {

    @Override
    public Collection<OrderedSystem> getDefaultContextSystems() {
        return List.of(
                OrderedSystem.wrap(new FirstTestSystem(), 1),
                OrderedSystem.wrap(new SecondTestSystem(), 2)
        );
    }
}
