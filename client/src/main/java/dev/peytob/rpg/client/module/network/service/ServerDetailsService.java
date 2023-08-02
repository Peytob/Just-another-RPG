package dev.peytob.rpg.client.module.network.service;


import dev.peytob.rpg.client.module.network.model.ServerConnectionDetails;

import java.util.concurrent.CompletableFuture;

public interface ServerDetailsService {

    CompletableFuture<ServerConnectionDetails> getServerConnectionDetails();
}
