package dev.peytob.rpg.server.base.rpc.world;

import com.google.protobuf.Empty;
import dev.peytob.rpg.core.module.base.model.world.tilemap.PlacedTile;
import dev.peytob.rpg.core.module.base.model.world.tilemap.Tilemap;
import dev.peytob.rpg.math.vector.Vec2i;
import dev.peytob.rpg.rpc.interfaces.base.model.WorldAccessorServiceGrpc;
import dev.peytob.rpg.rpc.interfaces.base.model.world.TileRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.model.world.TilemapRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.model.world.WorldRpcDto;
import dev.peytob.rpg.server.base.repository.WorldRepository;
import dev.peytob.rpg.server.base.service.user.UserService;
import dev.peytob.rpg.server.server.rpc.security.AuthServerInterceptor;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;

import static dev.peytob.rpg.server.server.rpc.utils.RpcMessageMapper.toVec2iDto;

@GrpcService(interceptors = AuthServerInterceptor.class)
public class RpcWorldAccessorService extends WorldAccessorServiceGrpc.WorldAccessorServiceImplBase {

    private final UserService userService;

    private final WorldRepository worldRepository;

    public RpcWorldAccessorService(UserService userService, WorldRepository worldRepository) {
        this.userService = userService;
        this.worldRepository = worldRepository;
    }

    @Override
    public void getWorld(Empty request, StreamObserver<WorldRpcDto> responseObserver) {
//        User user = userService.getUserById("test");
//        Player userPlayer = user.getWorldPlayer();
//        TilemapRpcDto tilemapRpcDto = toTilemapDto(userPlayer.getWorld().getTilemap());

        Tilemap tilemap = worldRepository.getById(1).getTilemap();
        responseObserver.onNext(toWorldDto(tilemap));
        responseObserver.onCompleted();
    }

    private WorldRpcDto toWorldDto(Tilemap tilemap) {
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
