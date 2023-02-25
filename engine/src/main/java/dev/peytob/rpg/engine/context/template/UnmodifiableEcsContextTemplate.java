package dev.peytob.rpg.engine.context.template;

import dev.peytob.rpg.ecs.system.OrderedSystem;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

class UnmodifiableEcsContextTemplate implements EcsContextTemplate {

    private final List<OrderedSystem> orderedSystems;

    public UnmodifiableEcsContextTemplate(List<OrderedSystem> orderedSystems) {
        this.orderedSystems = Collections.unmodifiableList(orderedSystems);
    }

    @Override
    public Collection<OrderedSystem> getDefaultContextSystems() {
        return orderedSystems;
    }
}
