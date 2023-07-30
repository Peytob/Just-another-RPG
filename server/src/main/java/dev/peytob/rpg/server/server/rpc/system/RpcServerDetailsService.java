package dev.peytob.rpg.server.server.rpc.system;

import com.google.protobuf.Empty;
import dev.peytob.rpg.rpc.interfaces.base.system.ServerConnectionDetailsRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.system.ServerDetailsServiceGrpc;
import dev.peytob.rpg.server.server.configuration.properties.GrpcServerDetailsProperties;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@GrpcService
@EnableConfigurationProperties(GrpcServerDetailsProperties.class)
public class RpcServerDetailsService extends ServerDetailsServiceGrpc.ServerDetailsServiceImplBase {

    private final GrpcServerDetailsProperties grpcServerProperties;

    public RpcServerDetailsService(GrpcServerDetailsProperties grpcServerProperties) {
        this.grpcServerProperties = grpcServerProperties;
    }

    @Override
    public void getServerDetails(Empty request, StreamObserver<ServerConnectionDetailsRpcDto> responseObserver) {
        ServerConnectionDetailsRpcDto serverConnectionDetails = ServerConnectionDetailsRpcDto.newBuilder()
            .setAddress(grpcServerProperties.address())
            .setName(grpcServerProperties.name())
            .setPort(grpcServerProperties.port())
            .build();

        responseObserver.onNext(serverConnectionDetails);
        responseObserver.onCompleted();
    }
}
