package dev.peytob.rpg.client.module.base.service.world;

import dev.peytob.rpg.core.module.base.model.level.tilemap.Tilemap;

import java.util.concurrent.CompletableFuture;

public interface WorldService {

    CompletableFuture<Tilemap> loadWorld();
}
