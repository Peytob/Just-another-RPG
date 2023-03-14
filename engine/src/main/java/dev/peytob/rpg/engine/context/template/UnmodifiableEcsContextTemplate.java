package dev.peytob.rpg.engine.context.template;

import dev.peytob.rpg.ecs.system.OrderedSystem;

import java.util.Collection;
import java.util.Collections;

class UnmodifiableEcsContextTemplate implements EcsContextTemplate {

    private final Collection<OrderedSystem> orderedSystems;

    public UnmodifiableEcsContextTemplate(Collection<OrderedSystem> orderedSystems) {
        this.orderedSystems = Collections.unmodifiableCollection(orderedSystems);
    }

    @Override
    public Collection<OrderedSystem> getDefaultContextSystems() {
        return orderedSystems;
    }
}
