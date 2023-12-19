package dev.peytob.rpg.client.network.service;

import io.grpc.CallCredentials;
import io.grpc.Channel;

public interface DynamicGrpcService {

    void refreshConnection(Channel channel, CallCredentials callCredentials);
}
