package dev.peytob.rpg.client.module.network.service.grpc.impl;

import com.google.common.util.concurrent.ListenableFuture;
import dev.peytob.rpg.client.module.network.mapper.GrpcDtoMapper;
import dev.peytob.rpg.client.module.network.service.WorldAccessorService;
import dev.peytob.rpg.client.module.network.service.grpc.DynamicGrpcService;
import dev.peytob.rpg.core.module.base.model.level.tilemap.Tilemap;
import dev.peytob.rpg.rpc.interfaces.base.model.WorldAccessorServiceGrpc;
import dev.peytob.rpg.rpc.interfaces.base.model.WorldAccessorServiceGrpc.WorldAccessorServiceFutureStub;
import dev.peytob.rpg.rpc.interfaces.base.model.world.TilemapRpcDto;
import io.grpc.Channel;
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
    public CompletableFuture<Tilemap> getTilemap() {
        ListenableFuture<TilemapRpcDto> tilemapFuture = worldAccessorServiceStub.getTilemap(EMPTY_MESSAGE);
        return toCompletableFuture(tilemapFuture).thenApply(grpcDtoMapper::toTilemap);
    }

    @Override
    public void changeGrpcChannel(Channel channel) {
        worldAccessorServiceStub = WorldAccessorServiceGrpc.newFutureStub(channel);
    }
}
