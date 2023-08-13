package dev.peytob.rpg.client.module.network.service.grpc;

import com.google.protobuf.Empty;
import io.grpc.CallCredentials;
import io.grpc.Channel;
import io.grpc.stub.AbstractStub;

/**
 * Base class for all GRPC stubs wrappers.
 * This is probably not the best solution for changing and updating channels dynamically. I think it will work as an
 * initial solution.
 */
public interface DynamicGrpcService {

    Empty EMPTY_MESSAGE = Empty.newBuilder().build();

    AbstractStub<?> updateStub(Channel channel, CallCredentials credentials);
}
