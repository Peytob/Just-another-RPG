package dev.peytob.rpg.client.network.service.grpc;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.Empty;
import dev.peytob.rpg.client.network.constants.NetworkUtils;
import dev.peytob.rpg.client.network.model.NetworkConnectionState;
import dev.peytob.rpg.client.network.model.User;
import dev.peytob.rpg.client.network.service.DynamicGrpcService;
import dev.peytob.rpg.client.network.service.ServerAuthService;
import dev.peytob.rpg.rpc.interfaces.base.model.UserRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.system.AuthDataRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.system.ServerAuthServiceGrpc;
import dev.peytob.rpg.rpc.interfaces.base.system.ServerAuthServiceGrpc.ServerAuthServiceFutureStub;
import io.grpc.CallCredentials;
import io.grpc.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static net.javacrumbs.futureconverter.java8guava.FutureConverter.toCompletableFuture;

@Service
@RequiredArgsConstructor
public class GrpcServerAuthService implements ServerAuthService, DynamicGrpcService {

    private ServerAuthServiceFutureStub stub;

    @Override
    public CompletableFuture<User> login(String username, String password, Channel channel) {
        AuthDataRpcDto authDataRpcDto = AuthDataRpcDto.newBuilder()
            .setUsername(username)
            .setPassword(password)
            .build();

        ServerAuthServiceFutureStub temporalStub = ServerAuthServiceGrpc.newFutureStub(channel);

        ListenableFuture<UserRpcDto> loginFuture = temporalStub.login(authDataRpcDto);
        return toCompletableFuture(loginFuture).thenApply(userRpc -> new User(userRpc.getUsername(), userRpc.getToken()));
    }

    @Override
    public CompletableFuture<Void> logout() {
        AuthDataRpcDto authDataRpcDto = AuthDataRpcDto.newBuilder().build();
        ListenableFuture<Empty> loginFuture = stub.logout(authDataRpcDto);
        return toCompletableFuture(loginFuture).thenApply(NetworkUtils::emptyResponseAsVoid);

    }

    @Override
    public void refreshConnection(Channel channel, CallCredentials callCredentials) {
        stub = ServerAuthServiceGrpc.newFutureStub(channel).withCallCredentials(callCredentials);
    }
}
