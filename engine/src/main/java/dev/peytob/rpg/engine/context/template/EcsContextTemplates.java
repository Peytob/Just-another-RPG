package dev.peytob.rpg.engine.context.template;

import dev.peytob.rpg.ecs.system.OrderedSystem;

import java.util.Collection;

public enum EcsContextTemplates {;

    private final static EcsContextTemplate EMPTY = new EmptyEcsContextTemplate();

    public static EcsContextTemplate empty() {
        return EMPTY;
    }

    public static EcsContextTemplate unmodifiable(Collection<OrderedSystem> systems) {
        return new UnmodifiableEcsContextTemplate(systems);
    }
}
