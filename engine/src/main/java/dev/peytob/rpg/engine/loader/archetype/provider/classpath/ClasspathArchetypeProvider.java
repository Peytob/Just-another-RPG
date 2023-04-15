package dev.peytob.rpg.engine.loader.archetype.provider.classpath;

import dev.peytob.rpg.engine.loader.archetype.provider.ArchetypeProvider;
import dev.peytob.rpg.engine.resource.Archetype;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Uses Spring-found implementations of ClasspathArchetypeFactories to create archetypes
 */
@Component
public final class ClasspathArchetypeProvider implements ArchetypeProvider {

    private final Collection<ClasspathArchetypeFactory> classpathArchetypeFactories;

    public ClasspathArchetypeProvider(Collection<ClasspathArchetypeFactory> classpathArchetypeFactories) {
        this.classpathArchetypeFactories = classpathArchetypeFactories;
    }

    @Override
    public Collection<ArchetypeTemplate> loadArchetypes() {
        return classpathArchetypeFactories.stream()
            .map(ClasspathArchetypeFactory::create)
            .toList();
    }
}
