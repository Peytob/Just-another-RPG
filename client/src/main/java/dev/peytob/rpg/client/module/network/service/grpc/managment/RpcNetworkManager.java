package dev.peytob.rpg.client.module.network.service.grpc.managment;

import io.grpc.Channel;

public interface RpcNetworkManager {

    void connectServices(Channel channel);
}
