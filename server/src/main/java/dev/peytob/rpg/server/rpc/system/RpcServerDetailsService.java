package dev.peytob.rpg.server.rpc.system;

import com.google.protobuf.Empty;
import dev.peytob.rpg.rpc.interfaces.base.system.ServerConnectionDetailsRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.system.ServerDetailsServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class RpcServerDetailsService extends ServerDetailsServiceGrpc.ServerDetailsServiceImplBase {

    // TODO Mocked implementation

    @Override
    public void getServerDetails(Empty request, StreamObserver<ServerConnectionDetailsRpcDto> responseObserver) {
        ServerConnectionDetailsRpcDto serverConnectionDetails = ServerConnectionDetailsRpcDto.newBuilder()
            .setAddress("server.mock.dev")
            .setName("Mocked server")
            .setPort(9090)
            .build();

        responseObserver.onNext(serverConnectionDetails);
        responseObserver.onCompleted();
    }
}
