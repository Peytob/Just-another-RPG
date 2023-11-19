package dev.peytob.rpg.server.network.rpc.service;

import com.google.protobuf.Empty;
import dev.peytob.rpg.rpc.interfaces.base.model.UserRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.system.AuthDataRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.system.ServerAuthServiceGrpc;
import dev.peytob.rpg.server.network.service.AuthService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import static dev.peytob.rpg.server.network.constant.DefaultMessages.EMPTY_MESSAGE;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class RpcServerAuthService extends ServerAuthServiceGrpc.ServerAuthServiceImplBase {

    private final AuthService authService;

    private final RpcContextService rpcContextService;

    @Override
    public void login(AuthDataRpcDto request, StreamObserver<UserRpcDto> responseObserver) {
        authService.login(request.getUsername(), request.getPassword());

        // TODO Return user info...
        responseObserver.onNext(UserRpcDto.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void logout(AuthDataRpcDto request, StreamObserver<Empty> responseObserver) {
        log.info("Some player is logout...");

        authService.logout(rpcContextService.getAuthenticationToken());

        responseObserver.onNext(EMPTY_MESSAGE);
        responseObserver.onCompleted();
    }
}
