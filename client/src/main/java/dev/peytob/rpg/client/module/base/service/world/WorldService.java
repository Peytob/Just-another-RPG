package dev.peytob.rpg.client.module.base.service.world;

import dev.peytob.rpg.core.module.base.model.world.World;

import java.util.concurrent.CompletableFuture;

public interface WorldService {

    CompletableFuture<World> loadWorld();
}
