package dev.peytob.rpg.ecs.context;

import dev.peytob.rpg.ecs.system.OrderedSystem;

import java.util.Collection;

public enum EcsContexts {;

    public static EcsContext mutable(Collection<OrderedSystem> systems) {
        return new SimpleEcsContext(systems);
    }
}
