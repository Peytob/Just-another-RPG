package dev.peytob.rpg.engine.context.template;

import dev.peytob.rpg.ecs.system.OrderedSystem;

import java.util.Collection;
import java.util.Collections;

class EmptyEcsContextTemplate implements EcsContextTemplate {

    @Override
    public Collection<OrderedSystem> getDefaultContextSystems() {
        return Collections.emptyList();
    }
}
