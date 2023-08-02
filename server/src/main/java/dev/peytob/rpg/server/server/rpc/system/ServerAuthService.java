package dev.peytob.rpg.server.server.rpc.system;

import com.google.protobuf.Empty;
import dev.peytob.rpg.rpc.interfaces.base.system.AuthDataRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.system.ServerAuthServiceGrpc;
import dev.peytob.rpg.server.server.service.AuthService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static dev.peytob.rpg.server.server.rpc.constant.DefaultMessages.EMPTY_MESSAGE;

@GrpcService
public class ServerAuthService extends ServerAuthServiceGrpc.ServerAuthServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(ServerAuthService.class);

    private final AuthService authService;

    public ServerAuthService(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void login(AuthDataRpcDto request, StreamObserver<AuthDataRpcDto> responseObserver) {
        log.info("New user is trying to login on server");

        authService.login(request.getToken());

        responseObserver.onNext(request);
        responseObserver.onCompleted();
    }

    @Override
    public void logout(AuthDataRpcDto request, StreamObserver<Empty> responseObserver) {
        log.info("Some player is logout...");

        authService.logout(request.getToken());

        responseObserver.onNext(EMPTY_MESSAGE);
        responseObserver.onCompleted();
    }
}
