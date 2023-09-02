package dev.peytob.rpg.client.module.network.service.grpc.impl;

import com.google.common.util.concurrent.ListenableFuture;
import dev.peytob.rpg.client.module.base.model.Player;
import dev.peytob.rpg.client.module.network.service.PlayerAccessorService;
import dev.peytob.rpg.client.module.network.service.grpc.DynamicGrpcService;
import dev.peytob.rpg.client.module.network.utils.RpcMessageMapper;
import dev.peytob.rpg.rpc.interfaces.base.model.PlayerRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.player.PlayerAccessorServiceGrpc;
import dev.peytob.rpg.rpc.interfaces.base.player.PlayerAccessorServiceGrpc.PlayerAccessorServiceFutureStub;
import io.grpc.CallCredentials;
import io.grpc.Channel;
import io.grpc.stub.AbstractStub;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static net.javacrumbs.futureconverter.java8guava.FutureConverter.toCompletableFuture;

@Service
public class RpcPlayerAccessorService implements PlayerAccessorService, DynamicGrpcService {

    private PlayerAccessorServiceFutureStub playerAccessorServiceStub;

    @Override
    public CompletableFuture<Player> getMe() {
        ListenableFuture<PlayerRpcDto> listenableFeature = playerAccessorServiceStub.getMe(EMPTY_MESSAGE);
        return toCompletableFuture(listenableFeature).thenApply(RpcMessageMapper::toPlayerDto);
    }

    @Override
    public AbstractStub<?> updateStub(Channel channel, CallCredentials credentials) {
        playerAccessorServiceStub = PlayerAccessorServiceGrpc.newFutureStub(channel).withCallCredentials(credentials);
        return playerAccessorServiceStub;
    }
}
