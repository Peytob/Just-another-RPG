package dev.peytob.rpg.engine.pipeline.step;

import dev.peytob.rpg.engine.exception.archetype.ArchetypeBuildingException;
import dev.peytob.rpg.engine.exception.archetype.ArchetypeRegisteringException;
import dev.peytob.rpg.engine.loader.archetype.provider.ArchetypeProvider;
import dev.peytob.rpg.engine.loader.archetype.provider.classpath.ArchetypeTemplate;
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
            .forEach(this::buildAndRegisterArchetype);
    }

    private Collection<ArchetypeTemplate> loadArchetypes(ArchetypeProvider archetypeProvider) {
        logger.info("Processing {} archetypes provider", archetypeProvider.getName());

        try {
            return archetypeProvider.loadArchetypes();
        } catch (Exception exception) {
            logger.error("Exception while loading archetypes from archetype provider");
            throw exception;
        }
    }

    private Archetype buildArchetype(ArchetypeTemplate archetypeTemplate, Integer id) {
        Archetype.ArchetypeBuilder archetypeBuilder = new Archetype.ArchetypeBuilder();

        archetypeTemplate
            .componentFactories()
            .forEach(componentFactory -> {
                boolean isComponentFactoryAdded = archetypeBuilder.addComponentFactory(componentFactory);

                if (!isComponentFactoryAdded) {
                    throw new ArchetypeBuildingException(
                        "Invalid archetype template while creating archetype resource! Template for archetype with id '"
                            + archetypeTemplate.textId() + "' contains more than 1 factory for component with class "
                            + componentFactory.getClass().getSimpleName()
                    );
                }
            });

        return archetypeBuilder.build(id, archetypeTemplate.textId());
    }

    private void buildAndRegisterArchetype(ArchetypeTemplate archetypeTemplate) {
        logger.debug("Building and registering archetype with text id '{}'", archetypeTemplate.textId());

        Archetype archetype = buildArchetype(archetypeTemplate, archetypeRepository.getCount() + 1);

        boolean isArchetypesAppended = archetypeRepository.append(archetype);

        if (!isArchetypesAppended) {
            throw new ArchetypeRegisteringException("Archetypes IDs conflict: more than 1 archetype with id '" + archetype.textId() + "'", archetype);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
