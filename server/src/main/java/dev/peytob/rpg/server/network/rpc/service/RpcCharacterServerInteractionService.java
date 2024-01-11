package dev.peytob.rpg.server.network.rpc.service;

import com.google.protobuf.Empty;
import dev.peytob.rpg.rpc.interfaces.base.gameplay.AvailableWorldStateDto;
import dev.peytob.rpg.rpc.interfaces.base.gameplay.CharacterServerInteractionServiceGrpc;
import dev.peytob.rpg.rpc.interfaces.base.gameplay.ClientEventsDto;
import dev.peytob.rpg.rpc.interfaces.base.gameplay.EnterToServerDto;
import dev.peytob.rpg.server.gameplay.resource.Character;
import dev.peytob.rpg.server.gameplay.service.context.CharacterContextInteractionService;
import dev.peytob.rpg.server.gameplay.service.data.CharacterService;
import dev.peytob.rpg.server.network.dto.TokenDto;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import static dev.peytob.rpg.server.network.constant.DefaultMessages.EMPTY_MESSAGE;

@GrpcService
@RequiredArgsConstructor
@Slf4j
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

    @Override
    public StreamObserver<ClientEventsDto> synchronizeState(StreamObserver<AvailableWorldStateDto> responseObserver) {
        TokenDto token = rpcContextService.getAuthenticationTokenData();
        log.info("Creating new bidirectional synchronizing state streaming for user {}", token.userId());

        return new StreamObserver<>() {
            @Override
            public void onNext(ClientEventsDto value) {
                log.trace("Computing available information for character {} (user: {})", value.getCharacterId(), token.userId());

                AvailableWorldStateDto worldStateDto = AvailableWorldStateDto.newBuilder()
                    .setCharacterId(value.getCharacterId())
                    .build();

                responseObserver.onNext(worldStateDto);
            }

            @Override
            public void onError(Throwable t) {
                log.info("Exception while synchronizing client-server states with user {}", token.userId());
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                log.info("Synchronizing client-server states with user {} was completed. Goodbye, friend!", token.userId());
                responseObserver.onCompleted();
            }
        };
    }
}
