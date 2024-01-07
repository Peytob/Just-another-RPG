package dev.peytob.rpg.client.network.service;

import java.util.concurrent.CompletableFuture;

public interface CharacterServerInteractionService {

    CompletableFuture<Void> enterToServer(String characterId);
}
