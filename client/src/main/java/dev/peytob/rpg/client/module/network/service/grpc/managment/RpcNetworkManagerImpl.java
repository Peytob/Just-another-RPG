package dev.peytob.rpg.client.module.network.service.grpc.managment;

import dev.peytob.rpg.client.module.network.model.*;
import dev.peytob.rpg.client.module.network.service.ServerAuthService;
import dev.peytob.rpg.client.module.network.service.grpc.DynamicGrpcService;
import io.grpc.*;
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

    private final ServerAuthService serverAuthService;

    private final NetworkConnectionState networkConnectionState;

    public RpcNetworkManagerImpl(Collection<DynamicGrpcService> grpcServices, ServerAuthService serverAuthService, NetworkConnectionState networkConnectionState) {
        this.grpcServices = grpcServices;
        this.serverAuthService = serverAuthService;
        this.networkConnectionState = networkConnectionState;
    }

    @Override
    public Future<ServerAuth> loginOnServer(String username, String password, ServerConnectionDetails serverConnectionDetails) {
        log.info("Connecting to server: {}", serverConnectionDetails);

        // TODO Make request to auth server
        String token = username + ":" + password;

        Channel channel = ManagedChannelBuilder.forAddress(serverConnectionDetails.serverAddress(), serverConnectionDetails.port())
                .usePlaintext()
                .build();

        CallCredentials credentials = new BearerTokenCredentials(token);

        grpcServices.forEach(grpcService -> grpcService.updateStub(channel, credentials));
        networkConnectionState.setServerConnectionDetails(serverConnectionDetails);

        return serverAuthService.login(token).thenApply(user -> {
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

        return serverAuthService.logout(networkConnectionState.getServerAuth().token()).thenApply(literallyVoid -> {
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
