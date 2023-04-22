package dev.peytob.rpg.engine.loader.archetype.provider;

import dev.peytob.rpg.engine.loader.archetype.provider.classpath.ArchetypeTemplate;

import java.util.Collection;

public interface ArchetypeProvider {

    Collection<ArchetypeTemplate> loadArchetypes();

    default String getName() {
        return getClass().getSimpleName();
    }
}
