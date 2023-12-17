package dev.peytob.rpg.server.loader.pipeline;

import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import dev.peytob.rpg.engine.repositry.Repository;
import dev.peytob.rpg.engine.resource.Resource;
import dev.peytob.rpg.server.loader.service.loader.ResourceDataLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.GenericTypeResolver;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractResourceLoadingInitializingStep<T extends Resource> implements InitializingPipelineStep {

    private final Collection<ResourceDataLoader<T>> resourceProviders;

    private final Repository<T> resourceRepository;

    @Override
    public void execute() {
        Collection<T> resources = resourceProviders.stream()
            .map(ResourceDataLoader::loadResources)
            .flatMap(Collection::stream)
            .toList();

        resources.forEach(resourceRepository::append);

        if (log.isDebugEnabled()) {
            String loadedResourcesList = resources.stream()
                .map(Resource::id)
                .collect(Collectors.joining(";\n"));

            log.debug("Loaded {} resources of type: {}. Ids list:\n{}", resources.size(), getResourceTypeName(), loadedResourcesList);
        } else {
            log.info("Loaded {} resources of type: {}", resources.size(), getResourceTypeName());
        }
    }

    private String getResourceTypeName() {
        return GenericTypeResolver
            .resolveTypeArgument(getClass(), AbstractResourceLoadingInitializingStep.class)
            .getSimpleName();
    }
}
