package dev.peytob.rpg.client.module.base.service.network.service.grpc;

import dev.peytob.rpg.client.module.base.service.network.grpc.DynamicGrpcService;
import io.grpc.Channel;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RpcNetworkManagerImpl implements RpcNetworkManager {

    private final Collection<DynamicGrpcService> grpcServices;

    public RpcNetworkManagerImpl(Collection<DynamicGrpcService> grpcServices) {
        this.grpcServices = grpcServices;
    }

    @Override
    public void connectServices(Channel channel) {
        grpcServices.forEach(grpcService -> grpcService.changeGrpcChannel(channel));
    }
}
