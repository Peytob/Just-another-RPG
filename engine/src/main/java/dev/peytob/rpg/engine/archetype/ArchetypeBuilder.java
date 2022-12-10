package dev.peytob.rpg.engine.archetype;

import java.util.Collection;

public interface ArchetypeBuilder {

    boolean addComponentFactory(ComponentFactory<?> componentFactory);

    Collection<ComponentFactory<?>> getComponentFactories();

    Archetype build();
}
