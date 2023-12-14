package dev.peytob.rpg.server.loader.pipeline;

import dev.peytob.rpg.core.gameplay.model.world.World;
import dev.peytob.rpg.engine.repositry.Repository;
import dev.peytob.rpg.server.loader.service.loader.ResourceDataLoader;
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
