package dev.peytob.rpg.client.module.network.service.grpc.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.Empty;
import dev.peytob.rpg.client.module.network.model.User;
import dev.peytob.rpg.client.module.network.service.ServerAuthService;
import dev.peytob.rpg.client.module.network.service.grpc.DynamicGrpcService;
import dev.peytob.rpg.rpc.interfaces.base.model.UserRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.system.AuthDataRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.system.ServerAuthServiceGrpc;
import dev.peytob.rpg.rpc.interfaces.base.system.ServerAuthServiceGrpc.ServerAuthServiceFutureStub;
import io.grpc.CallCredentials;
import io.grpc.Channel;
import io.grpc.stub.AbstractStub;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static net.javacrumbs.futureconverter.java8guava.FutureConverter.toCompletableFuture;

@Service
public class RpcServerAuthService implements ServerAuthService, DynamicGrpcService {

    private ServerAuthServiceFutureStub serverAuthServiceStub;

    @Override
    public CompletableFuture<User> login(String token) {
        // TODO Make login by backend auth service
        ListenableFuture<UserRpcDto> loginFuture = serverAuthServiceStub.login(createAuthData(token));
        return toCompletableFuture(loginFuture).thenApply(ignored -> new User());
    }

    @Override
    public CompletableFuture<Void> logout(String token) {
        ListenableFuture<Empty> logoutFuture = serverAuthServiceStub.logout(createAuthData(token));
        return toCompletableFuture(logoutFuture).thenApply(empty -> null);
    }

    @Override
    public AbstractStub<ServerAuthServiceFutureStub> updateStub(Channel channel, CallCredentials credentials) {
        serverAuthServiceStub = ServerAuthServiceGrpc.newFutureStub(channel).withCallCredentials(credentials);
        return serverAuthServiceStub;
    }

    private AuthDataRpcDto createAuthData(String token) {
        return AuthDataRpcDto.newBuilder()
                .setToken(token)
                .build();
    }
}
