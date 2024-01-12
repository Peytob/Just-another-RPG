package dev.peytob.rpg.client.network.service.grpc;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.Empty;
import dev.peytob.rpg.client.network.constants.NetworkUtils;
import dev.peytob.rpg.client.network.service.CharacterServerInteractionService;
import dev.peytob.rpg.client.network.service.DynamicGrpcService;
import dev.peytob.rpg.rpc.interfaces.base.gameplay.AvailableWorldStateDto;
import dev.peytob.rpg.rpc.interfaces.base.gameplay.CharacterServerInteractionServiceGrpc;
import dev.peytob.rpg.rpc.interfaces.base.gameplay.CharacterServerInteractionServiceGrpc.CharacterServerInteractionServiceFutureStub;
import dev.peytob.rpg.rpc.interfaces.base.gameplay.CharacterServerInteractionServiceGrpc.CharacterServerInteractionServiceStub;
import dev.peytob.rpg.rpc.interfaces.base.gameplay.ClientEventsDto;
import dev.peytob.rpg.rpc.interfaces.base.gameplay.EnterToServerDto;
import io.grpc.CallCredentials;
import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static net.javacrumbs.futureconverter.java8guava.FutureConverter.toCompletableFuture;

@Service
@RequiredArgsConstructor
public class GrpcCharacterServerInteractionService implements CharacterServerInteractionService, DynamicGrpcService {

    private CharacterServerInteractionServiceFutureStub futureStub;
    
    private CharacterServerInteractionServiceStub syncStub;

    @Override
    public CompletableFuture<Void> enterToServer(String characterId) {
        EnterToServerDto characterDto = EnterToServerDto.newBuilder()
            .setCharacterId(characterId)
            .build();

        ListenableFuture<Empty> enterFuture = futureStub.enterToServer(characterDto);
        return toCompletableFuture(enterFuture).thenApply(NetworkUtils::emptyResponseAsVoid);
    }

    @Override
    public StreamObserver<ClientEventsDto> synchronizeState(StreamObserver<AvailableWorldStateDto> responseObserver) {
        return syncStub.synchronizeState(responseObserver);
    }

    @Override
    public void refreshConnection(Channel channel, CallCredentials callCredentials) {
        this.futureStub = CharacterServerInteractionServiceGrpc.newFutureStub(channel).withCallCredentials(callCredentials);
        this.syncStub = CharacterServerInteractionServiceGrpc.newStub(channel).withCallCredentials(callCredentials);
    }
}
