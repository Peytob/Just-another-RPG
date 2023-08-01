package dev.peytob.rpg.client.module.network.service.grpc.managment;

import com.google.common.util.concurrent.Futures;
import dev.peytob.rpg.client.module.network.model.ServerAuth;
import dev.peytob.rpg.client.module.network.model.ServerConnectionDetails;
import dev.peytob.rpg.client.module.network.service.AuthService;
import dev.peytob.rpg.client.module.network.service.grpc.DynamicGrpcService;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureAdapter;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

@Service
public class RpcNetworkManagerImpl implements RpcNetworkManager {

    private final Collection<DynamicGrpcService> grpcServices;

    private final AuthService authService;

    private final Executor executor;

    private ServerConnectionDetails serverConnectionDetails;

    public RpcNetworkManagerImpl(Collection<DynamicGrpcService> grpcServices, AuthService authService, Executor executor) {
        this.grpcServices = grpcServices;
        this.authService = authService;
        this.executor = executor;
        this.serverConnectionDetails = null;
    }

    @Override
    public Future<ServerAuth> loginOnServer(String username, String password, ServerConnectionDetails serverConnectionDetails) {
        Future<String> loginFuture = authService.login(username, password);
    }

    @Override
    public Future<ServerConnectionDetails> logoutFromServer() {

    }

    @Override
    public ServerAuth getServerAuth() {
    }

    @Override
    public boolean isConnectedToServer() {
    }
}
