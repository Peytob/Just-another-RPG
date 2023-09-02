package dev.peytob.rpg.server.base.rpc.player;

import com.google.protobuf.Empty;
import dev.peytob.rpg.rpc.interfaces.base.model.Vec2RpcDto;
import dev.peytob.rpg.rpc.interfaces.base.player.PlayerMovingServiceGrpc;
import dev.peytob.rpg.server.base.resource.world.entity.Player;
import dev.peytob.rpg.server.base.service.control.EntityMovingService;
import dev.peytob.rpg.server.server.rpc.context.RpcContextService;
import dev.peytob.rpg.server.server.rpc.security.AuthServerInterceptor;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import static dev.peytob.rpg.server.server.rpc.constant.DefaultMessages.EMPTY_MESSAGE;
import static dev.peytob.rpg.server.server.rpc.utils.RpcMessageMapper.toVec2;

@GrpcService(interceptors = AuthServerInterceptor.class)
public class RpcPlayerMovingService extends PlayerMovingServiceGrpc.PlayerMovingServiceImplBase {

    private final RpcContextService rpcContextService;

    private final EntityMovingService entityMovingService;

    public RpcPlayerMovingService(RpcContextService rpcContextService, EntityMovingService entityMovingService) {
        this.rpcContextService = rpcContextService;
        this.entityMovingService = entityMovingService;
    }

    @Override
    public void directionalMove(Vec2RpcDto request, StreamObserver<Empty> responseObserver) {
        Player worldPlayer = rpcContextService.getAuthWorldPlayer();
        entityMovingService.moveEntity(worldPlayer, toVec2(request));
        responseObserver.onNext(EMPTY_MESSAGE);
        responseObserver.onCompleted();
    }
}
