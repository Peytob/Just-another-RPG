package dev.peytob.rpg.client.module.network.service.grpc.impl;

import com.google.common.util.concurrent.ListenableFuture;
import dev.peytob.rpg.client.module.network.mapper.GrpcDtoMapper;
import dev.peytob.rpg.client.module.network.service.WorldAccessorService;
import dev.peytob.rpg.client.module.network.service.grpc.DynamicGrpcService;
import dev.peytob.rpg.core.module.base.model.world.World;
import dev.peytob.rpg.rpc.interfaces.base.model.WorldAccessorServiceGrpc;
import dev.peytob.rpg.rpc.interfaces.base.model.WorldAccessorServiceGrpc.WorldAccessorServiceFutureStub;
import dev.peytob.rpg.rpc.interfaces.base.model.world.WorldRpcDto;
import io.grpc.CallCredentials;
import io.grpc.Channel;
import io.grpc.stub.AbstractStub;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static net.javacrumbs.futureconverter.java8guava.FutureConverter.toCompletableFuture;

@Service
public class RpcWorldAccessorService implements WorldAccessorService, DynamicGrpcService {

    private final GrpcDtoMapper grpcDtoMapper;

    private WorldAccessorServiceFutureStub worldAccessorServiceStub;

    public RpcWorldAccessorService(GrpcDtoMapper grpcDtoMapper) {
        this.grpcDtoMapper = grpcDtoMapper;
    }

    @Override
    public CompletableFuture<World> getWorld() {
        ListenableFuture<WorldRpcDto> worldFuture = worldAccessorServiceStub.getWorld(EMPTY_MESSAGE);
        return toCompletableFuture(worldFuture).thenApply(grpcDtoMapper::toWorld);
    }

    @Override
    public AbstractStub<WorldAccessorServiceFutureStub> updateStub(Channel channel, CallCredentials credentials) {
        worldAccessorServiceStub = WorldAccessorServiceGrpc.newFutureStub(channel).withCallCredentials(credentials);
        return worldAccessorServiceStub;
    }
}
