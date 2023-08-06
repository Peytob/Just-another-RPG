package dev.peytob.rpg.client.module.network.service;

import dev.peytob.rpg.core.module.base.model.level.tilemap.Tilemap;

import java.util.concurrent.CompletableFuture;

public interface WorldAccessorService {

    CompletableFuture<Tilemap> getTilemap();
}
