package dev.peytob.rpg.server.base.rpc.player;

import com.google.protobuf.Empty;
import dev.peytob.rpg.rpc.interfaces.base.model.PlayerRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.model.Vec2RpcDto;
import dev.peytob.rpg.rpc.interfaces.base.player.PlayerAccessorServiceGrpc;
import dev.peytob.rpg.rpc.interfaces.base.player.PlayerMovingServiceGrpc;
import dev.peytob.rpg.server.base.resource.world.entity.Player;
import dev.peytob.rpg.server.base.service.control.EntityMovingService;
import dev.peytob.rpg.server.server.rpc.context.RpcContextService;
import dev.peytob.rpg.server.server.rpc.security.AuthServerInterceptor;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import static dev.peytob.rpg.server.server.rpc.utils.RpcMessageMapper.toPlayerDto;

@GrpcService(interceptors = AuthServerInterceptor.class)
public class RpcPlayerAccessorService extends PlayerAccessorServiceGrpc.PlayerAccessorServiceImplBase {

    private final RpcContextService rpcContextService;

    public RpcPlayerAccessorService(RpcContextService rpcContextService, EntityMovingService entityMovingService) {
        this.rpcContextService = rpcContextService;
    }

    @Override
    public void getMe(Empty request, StreamObserver<PlayerRpcDto> responseObserver) {
        Player authWorldPlayer = rpcContextService.getAuthWorldPlayer();
        responseObserver.onNext(toPlayerDto(authWorldPlayer));
        responseObserver.onCompleted();
    }
}