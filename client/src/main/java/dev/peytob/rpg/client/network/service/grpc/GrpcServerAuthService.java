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
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static net.javacrumbs.futureconverter.java8guava.FutureConverter.toCompletableFuture;

@Service
public class GrpcServerAuthService implements ServerAuthService, DynamicGrpcService {

    private ServerAuthServiceFutureStub stub;

    private final NetworkConnectionState networkConnectionState;

    public GrpcServerAuthService(NetworkConnectionState networkConnectionState) {
        this.networkConnectionState = networkConnectionState;
    }

    @Override
    public CompletableFuture<User> login() {
        String serverToken = networkConnectionState.getServerToken();
        AuthDataRpcDto authDataRpcDto = AuthDataRpcDto.newBuilder().setToken(serverToken).build();
        ListenableFuture<UserRpcDto> loginFuture = stub.login(authDataRpcDto);
        return toCompletableFuture(loginFuture).thenApply(userRpc -> new User());
    }

    @Override
    public CompletableFuture<Void> logout() {
        String serverToken = networkConnectionState.getServerToken();
        AuthDataRpcDto authDataRpcDto = AuthDataRpcDto.newBuilder().setToken(serverToken).build();
        ListenableFuture<Empty> loginFuture = stub.logout(authDataRpcDto);
        return toCompletableFuture(loginFuture).thenApply(NetworkUtils::emptyResponseAsVoid);

    }

    @Override
    public void refreshConnection(Channel channel, CallCredentials callCredentials) {
        stub = ServerAuthServiceGrpc.newFutureStub(channel).withCallCredentials(callCredentials);
    }
}