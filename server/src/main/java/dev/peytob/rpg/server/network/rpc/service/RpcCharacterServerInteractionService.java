package dev.peytob.rpg.server.network.rpc.service;

import com.google.protobuf.Empty;
import dev.peytob.rpg.rpc.interfaces.base.gameplay.CharacterServerInteractionServiceGrpc;
import dev.peytob.rpg.rpc.interfaces.base.gameplay.EnterToServerDto;
import dev.peytob.rpg.server.gameplay.resource.Character;
import dev.peytob.rpg.server.gameplay.service.context.CharacterContextInteractionService;
import dev.peytob.rpg.server.gameplay.service.data.CharacterService;
import dev.peytob.rpg.server.network.dto.TokenDto;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import static dev.peytob.rpg.server.network.constant.DefaultMessages.EMPTY_MESSAGE;

@GrpcService
@RequiredArgsConstructor
public class RpcCharacterServerInteractionService extends CharacterServerInteractionServiceGrpc.CharacterServerInteractionServiceImplBase {

    private final RpcContextService rpcContextService;

    private final CharacterService characterService;

    private final CharacterContextInteractionService characterContextInteractionService;

    @Override
    public void enterToServer(EnterToServerDto request, StreamObserver<Empty> responseObserver) {
        TokenDto token = rpcContextService.getAuthenticationTokenData();
        Character character = characterService.getCharacterById(request.getCharacterId());

        if (!token.userId().equals(character.userId())) {
            responseObserver.onError(new IllegalAccessException());
            responseObserver.onCompleted();
            return;
        }

        characterContextInteractionService.enterToContext(character, "testWorld");

        responseObserver.onNext(EMPTY_MESSAGE);
        responseObserver.onCompleted();
    }
}
