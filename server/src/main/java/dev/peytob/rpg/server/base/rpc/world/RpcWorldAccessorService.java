package dev.peytob.rpg.server.base.rpc.world;

import com.google.protobuf.Empty;
import dev.peytob.rpg.core.module.base.model.world.tilemap.PlacedTile;
import dev.peytob.rpg.core.module.base.model.world.tilemap.Tilemap;
import dev.peytob.rpg.math.vector.Vec2i;
import dev.peytob.rpg.rpc.interfaces.base.model.WorldAccessorServiceGrpc;
import dev.peytob.rpg.rpc.interfaces.base.model.world.TileRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.model.world.TilemapRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.model.world.WorldRpcDto;
import dev.peytob.rpg.server.base.resource.world.World;
import dev.peytob.rpg.server.server.rpc.context.RpcContextService;
import dev.peytob.rpg.server.server.rpc.security.AuthServerInterceptor;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;

import static dev.peytob.rpg.server.server.rpc.utils.RpcMessageMapper.toVec2iDto;

@GrpcService(interceptors = AuthServerInterceptor.class)
public class RpcWorldAccessorService extends WorldAccessorServiceGrpc.WorldAccessorServiceImplBase {

    private final RpcContextService rpcContextService;

    public RpcWorldAccessorService(RpcContextService rpcContextService) {
        this.rpcContextService = rpcContextService;
    }

    @Override
    public void getWorld(Empty request, StreamObserver<WorldRpcDto> responseObserver) {
        World world = rpcContextService.getAuthWorldPlayer().getWorld();
        responseObserver.onNext(toWorldDto(world));
        responseObserver.onCompleted();
    }

    private WorldRpcDto toWorldDto(World world) {
        Tilemap tilemap = world.getTilemap();
        TilemapRpcDto.Builder tilemapBuilder = TilemapRpcDto.newBuilder();

        Vec2i sizes = tilemap.getSizes();
        List<TileRpcDto> tiles = new ArrayList<>(sizes.x() * sizes.y());
        for (int x = 0; x < sizes.x(); x++) {
            for (int y = 0; y < sizes.y(); ++y) {
                PlacedTile tile = tilemap.getTile(x, y);

                if (tile == null) {
                    continue;
                }

                TileRpcDto tileDto = TileRpcDto.newBuilder()
                    .setPosition(toVec2iDto(tile.gridPosition()))
                    .setTextId(tile.tile().textId())
                    .build();

                tiles.add(tileDto);
            }
        }

        tilemapBuilder.addAllTiles(tiles);
        tilemapBuilder.setSizes(toVec2iDto(tilemap.getSizes()));

        return WorldRpcDto.newBuilder().setTilemap(tilemapBuilder.build()).build();
    }
}
