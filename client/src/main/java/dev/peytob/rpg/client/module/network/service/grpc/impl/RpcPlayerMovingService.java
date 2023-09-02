package dev.peytob.rpg.client.module.network.service.grpc.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.Empty;
import dev.peytob.rpg.client.module.network.service.PlayerMovingService;
import dev.peytob.rpg.client.module.network.service.grpc.DynamicGrpcService;
import dev.peytob.rpg.math.vector.Vec2;
import dev.peytob.rpg.rpc.interfaces.base.player.PlayerMovingServiceGrpc;
import dev.peytob.rpg.rpc.interfaces.base.player.PlayerMovingServiceGrpc.PlayerMovingServiceFutureStub;
import io.grpc.CallCredentials;
import io.grpc.Channel;
import io.grpc.stub.AbstractStub;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static dev.peytob.rpg.client.module.network.utils.RpcMessageMapper.toVec2Dto;
import static net.javacrumbs.futureconverter.java8guava.FutureConverter.toCompletableFuture;

@Service
public class RpcPlayerMovingService implements PlayerMovingService, DynamicGrpcService {

    private PlayerMovingServiceFutureStub playerMovingServiceStub;

    @Override
    public CompletableFuture<Void> directionalMove(Vec2 directional) {
        ListenableFuture<Empty> future = playerMovingServiceStub.directionalMove(toVec2Dto(directional));
        return toCompletableFuture(future).thenAccept(empty -> {});
    }

    @Override
    public AbstractStub<?> updateStub(Channel channel, CallCredentials credentials) {
        playerMovingServiceStub = PlayerMovingServiceGrpc.newFutureStub(channel).withCallCredentials(credentials);
        return playerMovingServiceStub;
    }
}
