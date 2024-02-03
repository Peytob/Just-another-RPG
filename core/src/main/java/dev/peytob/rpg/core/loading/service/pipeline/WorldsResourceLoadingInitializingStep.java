package dev.peytob.rpg.core.loading.service.pipeline;

import dev.peytob.rpg.core.gameplay.resource.World;
import dev.peytob.rpg.engine.repositry.Repository;
import dev.peytob.rpg.core.loading.service.loader.ResourceDataLoader;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class WorldsResourceLoadingInitializingStep extends AbstractResourceLoadingInitializingStep<World> {

    public WorldsResourceLoadingInitializingStep(Collection<ResourceDataLoader<World>> resourceProviders, Repository<World> resourceRepository) {
        super(resourceProviders, resourceRepository);
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
