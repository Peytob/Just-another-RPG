package dev.peytob.rpg.engine.pipeline.step;

import dev.peytob.rpg.engine.exception.archetype.ArchetypeRegisteringException;
import dev.peytob.rpg.engine.loader.archetype.provider.ArchetypeProvider;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import dev.peytob.rpg.engine.repositry.Repository;
import dev.peytob.rpg.engine.resource.Archetype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public final class LoadArchetypesInitializingPipelineStep implements InitializingPipelineStep {

    private static final Logger logger = LoggerFactory.getLogger(LoadArchetypesInitializingPipelineStep.class);

    private final Repository<Archetype> archetypeRepository;

    private final Collection<ArchetypeProvider> archetypeProviders;

    public LoadArchetypesInitializingPipelineStep(Repository<Archetype> archetypeRepository, Collection<ArchetypeProvider> archetypeProviders) {
        this.archetypeRepository = archetypeRepository;
        this.archetypeProviders = archetypeProviders;
    }

    @Override
    public void execute() {
        // In the case of a large number of archetypes, it is worth loading them all first to display a list of
        // conflicting names and IDs

        if (archetypeProviders.isEmpty()) {
            logger.info("Archetypes providers not found");
        }

        archetypeProviders.stream()
            .flatMap(provider -> loadArchetypes(provider).stream())
            .forEach(this::registerArchetype);
    }

    private Collection<Archetype> loadArchetypes(ArchetypeProvider archetypeProvider) {
        logger.info("Processing {} archetypes provider", archetypeProvider.getName());

        try {
            return archetypeProvider.loadArchetypes();
        } catch (Exception exception) {
            logger.error("Exception while loading archetypes from archetype provider");
            throw exception;
        }
    }

    private void registerArchetype(Archetype archetype) {
        boolean isArchetypesAppended = archetypeRepository.append(archetype);

        if (!isArchetypesAppended) {
            throw new ArchetypeRegisteringException("Archetypes IDs conflict: more than 1 archetype with id " + archetype.textId(), archetype);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
