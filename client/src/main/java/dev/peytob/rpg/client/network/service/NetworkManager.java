package dev.peytob.rpg.client.network.service;

import dev.peytob.rpg.client.network.model.ServerCredentials;
import dev.peytob.rpg.client.network.model.ServerDetails;
import dev.peytob.rpg.client.network.model.User;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface NetworkManager {

    Collection<ServerDetails> getAvailableServers();

    Optional<ServerDetails> getConnectedServer();

    CompletableFuture<User> refreshConnection(ServerDetails serverDetails, ServerCredentials serverCredentials);
}
