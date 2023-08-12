package dev.peytob.rpg.client.module.network.service;

import dev.peytob.rpg.core.module.base.model.world.World;

import java.util.concurrent.CompletableFuture;

public interface WorldAccessorService {

    CompletableFuture<World> getWorld();
}
