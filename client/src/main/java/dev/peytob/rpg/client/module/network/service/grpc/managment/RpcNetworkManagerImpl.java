package dev.peytob.rpg.client.module.network.service.grpc.managment;

import dev.peytob.rpg.client.module.network.model.NetworkConnectionState;
import dev.peytob.rpg.client.module.network.model.ServerAuth;
import dev.peytob.rpg.client.module.network.model.ServerConnectionDetails;
import dev.peytob.rpg.client.module.network.service.AuthService;
import dev.peytob.rpg.client.module.network.service.grpc.DynamicGrpcService;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class RpcNetworkManagerImpl implements RpcNetworkManager {

    private static final Logger log = LoggerFactory.getLogger(RpcNetworkManagerImpl.class);

    private final Collection<DynamicGrpcService> grpcServices;

    private final AuthService authService;

    private final NetworkConnectionState networkConnectionState;

    public RpcNetworkManagerImpl(Collection<DynamicGrpcService> grpcServices, AuthService authService, NetworkConnectionState networkConnectionState) {
        this.grpcServices = grpcServices;
        this.authService = authService;
        this.networkConnectionState = networkConnectionState;
    }

    @Override
    public Future<ServerAuth> loginOnServer(String username, String password, ServerConnectionDetails serverConnectionDetails) {
        log.info("Connecting to server: {}", serverConnectionDetails);

        Channel channel = ManagedChannelBuilder.forAddress(serverConnectionDetails.address(), serverConnectionDetails.port())
            .usePlaintext()
            .build();

        grpcServices.forEach(grpcService -> grpcService.changeGrpcChannel(channel));
        networkConnectionState.setServerConnectionDetails(serverConnectionDetails);

        log.info("Login on server from server {}", serverConnectionDetails.address());

        return authService.login(username, password).thenApply(token -> {
            ServerAuth serverAuth = new ServerAuth(networkConnectionState.getServerConnectionDetails(), token);
            networkConnectionState.setServerAuth(serverAuth);
            return serverAuth;
        });
    }

    @Override
    public CompletableFuture<ServerConnectionDetails> logoutFromServer() {
        log.info("Logout from server");

        if (!networkConnectionState.isConnectedToServer()) {
            log.info("Logout is impossible: client is not connected to server");
            return CompletableFuture.completedFuture(null);
        }

        return authService.logout(networkConnectionState.getServerAuth().token()).thenApply(literallyVoid -> {
            ServerConnectionDetails serverConnectionDetails = networkConnectionState.getServerConnectionDetails();

            networkConnectionState.setServerAuth(null);
            networkConnectionState.setServerConnectionDetails(null);

            return serverConnectionDetails;
        });
    }

    @Override
    public ServerAuth getServerAuth() {
        return networkConnectionState.getServerAuth();
    }

    @Override
    public boolean isConnectedToServer() {
        return networkConnectionState.isConnectedToServer();
    }
}
