package dev.peytob.rpg.engine.state;

import dev.peytob.rpg.ecs.system.OrderedSystem;

import java.util.Collection;
import java.util.List;

public record EngineState(
        String name,
        Collection<OrderedSystem> systems
) {

    public EngineState {
        systems = List.copyOf(systems);
    }
}
