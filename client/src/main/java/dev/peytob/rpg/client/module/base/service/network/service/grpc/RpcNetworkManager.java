package dev.peytob.rpg.client.module.base.service.network.service.grpc;

import io.grpc.Channel;

public interface RpcNetworkManager {

    void connectServices(Channel channel);
}
