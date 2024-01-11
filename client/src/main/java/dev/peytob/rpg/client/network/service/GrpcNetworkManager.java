package dev.peytob.rpg.client.network.service;

import dev.peytob.rpg.client.network.model.*;
import dev.peytob.rpg.client.network.utils.AvailableWorldStateConverter;
import dev.peytob.rpg.client.network.utils.ClientEventsConverter;
import dev.peytob.rpg.client.network.utils.MappingResponseObserver;
import dev.peytob.rpg.core.network.model.client.ClientEvent;
import dev.peytob.rpg.core.network.model.server.WorldState;
import dev.peytob.rpg.rpc.interfaces.base.gameplay.AvailableWorldStateDto;
import dev.peytob.rpg.rpc.interfaces.base.gameplay.ClientEventsDto;
import io.grpc.CallCredentials;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
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

    private final CharacterServerInteractionService characterServerInteractionService;

    private final ServerAuthService serverAuthService;

    @Override
    public Collection<ServerDetails> getAvailableServers() {
        return Collections.emptyList();
    }

    @Override
    public Optional<ServerDetails> getConnectedServer() {
        return Optional.ofNullable(networkConnectionState.getConnectedServer());
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

    @Override
    public StreamObserver<Collection<ClientEvent>> startEventsStream(StreamObserver<WorldState> responseObserver) {
        log.info("Starting new client-server events stream");

        Converter<AvailableWorldStateDto, WorldState> worldStateConverter = new AvailableWorldStateConverter();
        StreamObserver<AvailableWorldStateDto> worldStateObserver = new MappingResponseObserver<>(responseObserver, worldStateConverter);

        Converter<Collection<ClientEvent>, ClientEventsDto> clientEventsConverter = new ClientEventsConverter();
        StreamObserver<ClientEventsDto> clientEventsDtoObserver = characterServerInteractionService.synchronizeState(worldStateObserver);

        networkConnectionState.setNetworkStreamObserver(clientEventsDtoObserver);

        return new MappingResponseObserver<>(clientEventsDtoObserver, clientEventsConverter);
    }

    @Override
    public void stopEventsStream() {
        log.info("Stopping client-server events stream");
        if (isEventsStreamingRunning()) {
            networkConnectionState.getNetworkStreamObserver().onCompleted();
            networkConnectionState.setNetworkStreamObserver(null);
        } else {
            throw new IllegalStateException("Trying to stop events stream with not active events stream.");
        }
    }

    @Override
    public boolean isEventsStreamingRunning() {
        return networkConnectionState.getConnectedServer() != null && networkConnectionState.getNetworkStreamObserver() != null;
    }
}
