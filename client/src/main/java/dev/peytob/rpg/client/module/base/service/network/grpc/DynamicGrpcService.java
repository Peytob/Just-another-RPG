package dev.peytob.rpg.client.module.base.service.network.grpc;

import com.google.protobuf.Empty;
import io.grpc.Channel;

/**
 * This is probably not the best solution for changing and updating channels dynamically. I think it will work as an
 * initial solution.
 */
public interface DynamicGrpcService {

    Empty EMPTY_MESSAGE = Empty.newBuilder().build();

    void changeGrpcChannel(Channel channel);
}
