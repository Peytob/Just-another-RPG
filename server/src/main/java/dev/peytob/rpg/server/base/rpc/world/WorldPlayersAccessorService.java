package dev.peytob.rpg.server.base.rpc.world;

import com.google.protobuf.Empty;
import dev.peytob.rpg.rpc.interfaces.base.model.PlayerDto;
import dev.peytob.rpg.rpc.interfaces.base.model.Vec2Dto;
import dev.peytob.rpg.rpc.interfaces.base.world.WorldPlayersAccessorServiceGrpc;
import dev.peytob.rpg.server.base.service.player.PlayerService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Collection;
import java.util.List;

@GrpcService
public class WorldPlayersAccessorService extends WorldPlayersAccessorServiceGrpc.WorldPlayersAccessorServiceImplBase {

    private static final Collection<PlayerDto> MOCKED_PLAYERS = List.of(
        PlayerDto.newBuilder()
            .setPosition(Vec2Dto
                .newBuilder()
                .setX(1).setY(5)
                .build())
            .build(),

        PlayerDto.newBuilder()
            .setPosition(Vec2Dto
                .newBuilder()
                .setX(-15).setY(15)
                .build())
            .build()
    );

    private final PlayerService playerService;

    public WorldPlayersAccessorService(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public void getNearPlayers(Empty request, StreamObserver<PlayerDto> responseObserver) {
        // TODO Temporary returns mocked players

        MOCKED_PLAYERS.forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }
}
