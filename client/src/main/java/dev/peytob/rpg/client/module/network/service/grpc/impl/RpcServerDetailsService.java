package dev.peytob.rpg.client.module.network.service.grpc.impl;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import dev.peytob.rpg.client.module.network.service.ServerDetailsService;
import dev.peytob.rpg.client.module.network.model.ServerConnectionDetails;
import dev.peytob.rpg.client.module.network.service.grpc.DynamicGrpcService;
import dev.peytob.rpg.rpc.interfaces.base.system.ServerConnectionDetailsRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.system.ServerDetailsServiceGrpc;
import dev.peytob.rpg.rpc.interfaces.base.system.ServerDetailsServiceGrpc.ServerDetailsServiceFutureStub;
import io.grpc.Channel;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class RpcServerDetailsService implements ServerDetailsService, DynamicGrpcService {

    private ServerDetailsServiceFutureStub serverDetailsServiceStub;

    private static ServerConnectionDetails convertToServerDetailsDto(ServerConnectionDetailsRpcDto details) {
        return new ServerConnectionDetails(details.getName(), details.getAddress(), details.getPort());
    }

    @Override
    public Future<ServerConnectionDetails> getServerConnectionDetails() {
        ListenableFuture<ServerConnectionDetailsRpcDto> detailsFuture = serverDetailsServiceStub.getServerDetails(EMPTY_MESSAGE);
        return Futures.lazyTransform(detailsFuture, RpcServerDetailsService::convertToServerDetailsDto);
    }

    @Override
    public void changeGrpcChannel(Channel channel) {
        serverDetailsServiceStub = ServerDetailsServiceGrpc.newFutureStub(channel);
    }
}