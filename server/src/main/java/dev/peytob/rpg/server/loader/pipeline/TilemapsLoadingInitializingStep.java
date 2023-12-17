package dev.peytob.rpg.server.loader.pipeline;

import dev.peytob.rpg.core.gameplay.resource.tilemap.Tilemap;
import dev.peytob.rpg.engine.repositry.Repository;
import dev.peytob.rpg.server.loader.service.loader.ResourceDataLoader;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class TilemapsLoadingInitializingStep extends AbstractResourceLoadingInitializingStep<Tilemap> {

    public TilemapsLoadingInitializingStep(Collection<ResourceDataLoader<Tilemap>> resourceProviders, Repository<Tilemap> resourceRepository) {
        super(resourceProviders, resourceRepository);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
