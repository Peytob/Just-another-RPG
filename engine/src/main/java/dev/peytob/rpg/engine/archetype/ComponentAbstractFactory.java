package dev.peytob.rpg.engine.archetype;

import dev.peytob.rpg.ecs.component.Component;

public interface ComponentAbstractFactory<T extends Component, P> {

    ComponentFactory<T> create(P pattern);

    Class<P> getPatternClass();

    String getSerializedName();
}
