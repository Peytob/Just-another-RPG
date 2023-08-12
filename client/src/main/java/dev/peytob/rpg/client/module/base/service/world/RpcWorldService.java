package dev.peytob.rpg.client.module.base.service.world;

import dev.peytob.rpg.client.module.network.service.WorldAccessorService;
import dev.peytob.rpg.core.module.base.model.world.World;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class RpcWorldService implements WorldService {

    private final WorldAccessorService worldAccessorService;

    public RpcWorldService(WorldAccessorService worldAccessorService) {
        this.worldAccessorService = worldAccessorService;
    }

    @Override
    public CompletableFuture<World> loadWorld() {
        return worldAccessorService.getWorld();
    }
}
