package dev.peytob.rpg.client.network.service;

import dev.peytob.rpg.client.network.model.*;
import io.grpc.CallCredentials;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class GrpcNetworkManager implements NetworkManager {

    private final Logger logger = LoggerFactory.getLogger(GrpcNetworkManager.class);

    private final Collection<DynamicGrpcService> grpcServices;

    private final NetworkConnectionState networkConnectionState;

    private final ServerAuthService serverAuthService;

    public GrpcNetworkManager(Collection<DynamicGrpcService> grpcServices, NetworkConnectionState networkConnectionState, ServerAuthService serverAuthService) {
        this.grpcServices = grpcServices;
        this.networkConnectionState = networkConnectionState;
        this.serverAuthService = serverAuthService;
    }

    @Override
    public Collection<ServerDetails> getAvailableServers() {
        return Collections.emptyList();
    }

    @Override
    public Optional<ServerDetails> getConnectedServer() {
        return Optional.empty();
    }

    @Override
    public CompletableFuture<User> refreshConnection(ServerDetails serverDetails, ServerCredentials serverCredentials) {
        logger.info("Connection to server {} as {}", serverDetails.serverAddress(), serverCredentials.username());

        // TODO Make request to auth server
        String token = serverCredentials.username() + ":" + serverCredentials.password();

        CallCredentials credentials = new BearerTokenCredentials(token);

        Channel channel = ManagedChannelBuilder.forAddress(serverDetails.serverAddress(), serverDetails.port())
                .usePlaintext()
                .build();

        grpcServices.forEach(dynamicGrpcService -> dynamicGrpcService.refreshConnection(channel, credentials));

        networkConnectionState.setConnectedServer(serverDetails);
        networkConnectionState.setServerToken(token);

        return serverAuthService.login();
    }
}
