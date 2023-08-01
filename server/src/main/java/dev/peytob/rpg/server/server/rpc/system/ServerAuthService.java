package dev.peytob.rpg.server.server.rpc.system;

import com.google.protobuf.Empty;
import dev.peytob.rpg.rpc.interfaces.base.system.AuthDataRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.system.ServerAuthServiceGrpc;
import dev.peytob.rpg.server.base.resource.entity.Player;
import dev.peytob.rpg.server.base.service.player.PlayerService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static dev.peytob.rpg.server.server.rpc.constant.DefaultMessages.EMPTY_MESSAGE;

@GrpcService
public class ServerAuthService extends ServerAuthServiceGrpc.ServerAuthServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(ServerAuthService.class);

    private final PlayerService playerService;

    public ServerAuthService(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public void login(AuthDataRpcDto request, StreamObserver<AuthDataRpcDto> responseObserver) {
        log.info("New user is trying to login on server");

        // TODO Temporary just creates new player

        // Getting user id by token...
        // Getting user data by id...
        // Making token -> user cache
        playerService.createPlayer(new Player(request.getToken().hashCode(), request.getToken()));
        // Sending player login events...

        responseObserver.onNext(request);
        responseObserver.onCompleted();
    }

    @Override
    public void logout(AuthDataRpcDto request, StreamObserver<Empty> responseObserver) {

        // Getting player by token...
        // Invalidating token -> user cache...
        playerService.removePlayer(new Player(request.getToken().hashCode(), request.getToken()));
        // Sending player logout events...

        responseObserver.onNext(EMPTY_MESSAGE);
        responseObserver.onCompleted();
    }
}
