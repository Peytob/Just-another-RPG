package dev.peytob.rpg.engine.module.archetype.loader.provider.classpath;

import dev.peytob.rpg.engine.module.archetype.loader.componentFactory.ComponentFactory;

import java.util.Collection;

/**
 * Mutable version of Archetype resource without id.
 * @param textId Future text id of resource.
 * @param componentFactories Component factories, that will be used by archetype. Should create different components!
 */
public record ArchetypeTemplate(
    String textId,
    Collection<ComponentFactory<?>> componentFactories
) {
}
