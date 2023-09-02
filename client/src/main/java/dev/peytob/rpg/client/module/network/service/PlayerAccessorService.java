package dev.peytob.rpg.client.module.network.service;

import dev.peytob.rpg.client.module.base.model.Player;

import java.util.concurrent.CompletableFuture;

public interface PlayerAccessorService {

    public CompletableFuture<Player> getMe();
}
