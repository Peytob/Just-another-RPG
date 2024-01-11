package dev.peytob.rpg.client.network.service;

import dev.peytob.rpg.client.network.model.ServerCredentials;
import dev.peytob.rpg.client.network.model.ServerDetails;
import dev.peytob.rpg.client.network.model.User;
import dev.peytob.rpg.core.network.model.client.ClientEvent;
import dev.peytob.rpg.core.network.model.server.WorldState;
import io.grpc.stub.StreamObserver;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface NetworkManager {

    Collection<ServerDetails> getAvailableServers();

    Optional<ServerDetails> getConnectedServer();

    StreamObserver<Collection<ClientEvent>> startEventsStream(StreamObserver<WorldState> responseObserver);

    void stopEventsStream();

    boolean isEventsStreamingRunning();

    CompletableFuture<User> refreshConnection(ServerDetails serverDetails, ServerCredentials serverCredentials);
}
