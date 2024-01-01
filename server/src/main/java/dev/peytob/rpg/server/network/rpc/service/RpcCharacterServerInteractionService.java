package dev.peytob.rpg.server.network.rpc.service;

import com.google.protobuf.Empty;
import dev.peytob.rpg.rpc.interfaces.base.gameplay.CharacterServerInteractionServiceGrpc;
import dev.peytob.rpg.rpc.interfaces.base.gameplay.EnterToServerDto;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import static dev.peytob.rpg.server.network.constant.DefaultMessages.EMPTY_MESSAGE;

@GrpcService
@RequiredArgsConstructor
public class RpcCharacterServerInteractionService extends CharacterServerInteractionServiceGrpc.CharacterServerInteractionServiceImplBase {

    private final RpcContextService rpcContextService;

    @Override
    public void enterToServer(EnterToServerDto request, StreamObserver<Empty> responseObserver) {
        rpcContextService.getAuthenticationToken();

        responseObserver.onNext(EMPTY_MESSAGE);
        responseObserver.onCompleted();
    }
}
