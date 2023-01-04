package dev.peytob.rpg.engine.archetype;

import dev.peytob.rpg.engine.resource.Resource;

import java.util.Collection;
import java.util.List;

public record Archetype(
        Integer id,
        String textId,
        Collection<ComponentFactory<?>> componentFactories
) implements Resource {

    public Archetype {
        componentFactories = List.copyOf(componentFactories);
    }
}
