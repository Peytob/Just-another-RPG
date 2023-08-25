package dev.peytob.rpg.client.module.network.service;

import dev.peytob.rpg.math.vector.Vec2;

import java.util.concurrent.CompletableFuture;

public interface PlayerMovingService {

    CompletableFuture<Void> directionalMove(Vec2 directional);
}
