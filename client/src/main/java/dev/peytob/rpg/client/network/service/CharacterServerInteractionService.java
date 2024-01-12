package dev.peytob.rpg.client.network.service;

import dev.peytob.rpg.rpc.interfaces.base.gameplay.AvailableWorldStateDto;
import dev.peytob.rpg.rpc.interfaces.base.gameplay.ClientEventsDto;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CompletableFuture;

public interface CharacterServerInteractionService {

    CompletableFuture<Void> enterToServer(String characterId);

    StreamObserver<ClientEventsDto> synchronizeState(StreamObserver<AvailableWorldStateDto> responseObserver);
}
