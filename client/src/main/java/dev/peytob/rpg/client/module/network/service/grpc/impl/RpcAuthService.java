package dev.peytob.rpg.client.module.network.service.grpc.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.Empty;
import dev.peytob.rpg.client.module.network.service.AuthService;
import dev.peytob.rpg.client.module.network.service.grpc.DynamicGrpcService;
import dev.peytob.rpg.rpc.interfaces.base.system.AuthDataRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.system.ServerAuthServiceGrpc;
import dev.peytob.rpg.rpc.interfaces.base.system.ServerAuthServiceGrpc.ServerAuthServiceFutureStub;
import io.grpc.Channel;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static net.javacrumbs.futureconverter.java8guava.FutureConverter.toCompletableFuture;

@Service
public class RpcAuthService implements AuthService, DynamicGrpcService {

    private ServerAuthServiceFutureStub serverAuthServiceStub;

    @Override
    public CompletableFuture<String> login(String username, String password) {
        // TODO Make login by backend auth service
        ListenableFuture<AuthDataRpcDto> loginFuture = serverAuthServiceStub.login(createAuthData(username + password));
        return toCompletableFuture(loginFuture).thenApply(AuthDataRpcDto::getToken);
    }

    @Override
    public CompletableFuture<Void> logout(String token) {
        ListenableFuture<Empty> logoutFuture = serverAuthServiceStub.logout(createAuthData(token));
        return toCompletableFuture(logoutFuture).thenApply(empty -> null);
    }

    private AuthDataRpcDto createAuthData(String token) {
        return AuthDataRpcDto.newBuilder()
            .setToken(token)
            .build();
    }

    @Override
    public void changeGrpcChannel(Channel channel) {
        serverAuthServiceStub = ServerAuthServiceGrpc.newFutureStub(channel);
    }
}
