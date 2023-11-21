package dev.peytob.rpg.server.network.rpc.service;

import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;
import dev.peytob.rpg.rpc.interfaces.base.model.UserRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.system.AuthDataRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.system.ServerAuthServiceGrpc;
import dev.peytob.rpg.server.network.dto.TokenDto;
import dev.peytob.rpg.server.network.service.AccountAuthService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import static dev.peytob.rpg.server.network.constant.DefaultMessages.EMPTY_MESSAGE;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class RpcServerAuthService extends ServerAuthServiceGrpc.ServerAuthServiceImplBase {

    private final AccountAuthService accountAuthService;

    private final RpcContextService rpcContextService;

    @Override
    public void login(AuthDataRpcDto request, StreamObserver<UserRpcDto> responseObserver) {
        String rawToken = accountAuthService.login(request.getUsername(), request.getPassword());
        TokenDto tokenDto = accountAuthService.validate(rawToken).orElseThrow(IllegalStateException::new);

        UserRpcDto userRpcDto = UserRpcDto.newBuilder()
            .setToken(rawToken)
            .setUsername(tokenDto.username())
            .setTokenExpiredAt(Timestamp.newBuilder()
                .setSeconds(tokenDto.tokenExpiredAt().getEpochSecond())
                .setNanos(tokenDto.tokenExpiredAt().getNano())
                .build())
            .build();

        responseObserver.onNext(userRpcDto);
        responseObserver.onCompleted();
    }

    @Override
    public void logout(AuthDataRpcDto request, StreamObserver<Empty> responseObserver) {
        log.info("Some player is logout...");

        accountAuthService.logout(rpcContextService.getAuthenticationToken());

        responseObserver.onNext(EMPTY_MESSAGE);
        responseObserver.onCompleted();
    }
}
