package dev.peytob.rpg.core.loading.service.pipeline;

import dev.peytob.rpg.core.gameplay.resource.Tile;
import dev.peytob.rpg.engine.repositry.Repository;
import dev.peytob.rpg.core.loading.service.loader.ResourceDataLoader;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class TilesLoadingInitializingStep extends AbstractResourceLoadingInitializingStep<Tile> {

    public TilesLoadingInitializingStep(Collection<ResourceDataLoader<Tile>> resourceProviders, Repository<Tile> resourceRepository) {
        super(resourceProviders, resourceRepository);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
