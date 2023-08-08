package dev.peytob.rpg.client.module.network.mapper;

import dev.peytob.rpg.core.module.base.model.world.World;
import dev.peytob.rpg.core.module.base.model.world.tilemap.Tilemap;
import dev.peytob.rpg.core.module.base.model.world.tilemap.Tilemaps;
import dev.peytob.rpg.core.module.base.resource.Tile;
import dev.peytob.rpg.math.vector.Vec2i;
import dev.peytob.rpg.rpc.interfaces.base.model.Vec2iRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.model.world.TileRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.model.world.TilemapRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.model.world.WorldRpcDto;
import org.springframework.stereotype.Service;

import static dev.peytob.rpg.math.vector.Vectors.immutableVec2i;

/**
 * TODO Decompose it!
 */
@Service
public class GrpcDtoMapperImpl implements GrpcDtoMapper {

    @Override
    public Vec2i toVec2i(Vec2iRpcDto dto) {
        return immutableVec2i(dto.getX(), dto.getY());
    }

    @Override
    public Tilemap toTilemap(TilemapRpcDto tilemapRpcDto) {
        Vec2i sizes = toVec2i(tilemapRpcDto.getSizes());
        Tilemap tilemap = Tilemaps.mutable(sizes);

        for (TileRpcDto tileRpcDto : tilemapRpcDto.getTilesList()) {
            Tile tile = new Tile(tileRpcDto.getTextId().hashCode(), tileRpcDto.getTextId());
            tilemap.setTile(tileRpcDto.getPosition().getX(), tileRpcDto.getPosition().getY(), tile);
        }

        return tilemap;
    }

    @Override
    public World toWorld(WorldRpcDto worldRpcDto) {
        return new World(
            toTilemap(worldRpcDto.getTilemap()));
    }
}
