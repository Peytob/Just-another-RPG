package dev.peytob.rpg.client.network.service;

import dev.peytob.rpg.client.network.model.*;
import io.grpc.CallCredentials;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class GrpcNetworkManager implements NetworkManager {

    private final Collection<DynamicGrpcService> grpcServices;

    private final NetworkConnectionState networkConnectionState;

    private final ServerAuthService serverAuthService;

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
        log.info("Connection to server {} as {}", serverDetails.serverAddress(), serverCredentials.username());

        Channel channel = ManagedChannelBuilder.forAddress(serverDetails.serverAddress(), serverDetails.port())
            .usePlaintext()
            .build();

        return serverAuthService.login(serverCredentials.username(), serverCredentials.password(), channel)
            .thenApply(user -> {
                log.info("Refreshing connection on gRPC services");

                String token = user.token();
                CallCredentials credentials = new BearerTokenCredentials(token);

                grpcServices.forEach(dynamicGrpcService -> dynamicGrpcService.refreshConnection(channel, credentials));

                networkConnectionState.setConnectedServer(serverDetails);
                networkConnectionState.setServerToken(token);

                log.info("gRPC services connection data has been refreshed");

                return user;
            });
    }
}
