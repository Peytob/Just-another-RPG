package dev.peytob.rpg.server.base.rpc;

import com.google.protobuf.Empty;
import dev.peytob.rpg.core.module.base.model.level.tilemap.PlacedTile;
import dev.peytob.rpg.core.module.base.model.level.tilemap.Tilemap;
import dev.peytob.rpg.math.vector.Vec2i;
import dev.peytob.rpg.rpc.interfaces.base.model.Vec2iRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.model.WorldAccessorServiceGrpc;
import dev.peytob.rpg.rpc.interfaces.base.model.world.TileRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.model.world.TilemapRpcDto;
import dev.peytob.rpg.server.base.repository.WorldRepository;
import dev.peytob.rpg.server.base.service.user.UserService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class WorldAccessorService extends WorldAccessorServiceGrpc.WorldAccessorServiceImplBase {

    private final UserService userService;

    private final WorldRepository worldRepository;

    public WorldAccessorService(UserService userService, WorldRepository worldRepository) {
        this.userService = userService;
        this.worldRepository = worldRepository;
    }

    @Override
    public void getTilemap(Empty request, StreamObserver<TilemapRpcDto> responseObserver) {
//        User user = userService.getUserById("test");
//        Player userPlayer = user.getWorldPlayer();
//        TilemapRpcDto tilemapRpcDto = toTilemapDto(userPlayer.getWorld().getTilemap());

        Tilemap tilemap = worldRepository.getById(1).getTilemap();
        responseObserver.onNext(toTilemapDto(tilemap));
        responseObserver.onCompleted();
    }

    private TilemapRpcDto toTilemapDto(Tilemap tilemap) {
        TilemapRpcDto.Builder builder = TilemapRpcDto.newBuilder();

        Vec2i sizes = tilemap.getSizes();
        List<TileRpcDto> tiles = new ArrayList<>(sizes.x() * sizes.y());
        for (int x = 0; x < sizes.x(); x++) {
            for (int y = 0; y < sizes.y(); ++y) {
                PlacedTile tile = tilemap.getTile(x, y);

                if (tile == null) {
                    continue;
                }

                TileRpcDto tileDto = TileRpcDto.newBuilder()
                    .setPosition(Vec2iRpcDto.newBuilder()
                        .setX(tile.gridPosition().x())
                        .setY(tile.gridPosition().y())
                        .build())
                    .setTextId(tile.tile().textId())
                    .build();

                tiles.add(tileDto);
            }
        }

        builder.addAllTiles(tiles);
        builder.setSizes(Vec2iRpcDto.newBuilder()
            .setX(sizes.x())
            .setY(sizes.y())
            .build());

        return builder.build();
    }
}
