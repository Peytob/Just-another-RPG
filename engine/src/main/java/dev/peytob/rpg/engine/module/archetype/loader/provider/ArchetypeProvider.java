package dev.peytob.rpg.engine.module.archetype.loader.provider;

import dev.peytob.rpg.engine.module.archetype.loader.provider.classpath.ArchetypeTemplate;

import java.util.Collection;

public interface ArchetypeProvider {

    Collection<ArchetypeTemplate> loadArchetypes();

    default String getName() {
        return getClass().getSimpleName();
    }
}
