package dev.peytob.rpg.client.module.network.utils;

import dev.peytob.rpg.core.module.base.model.world.World;
import dev.peytob.rpg.core.module.base.model.world.tilemap.Tilemap;
import dev.peytob.rpg.core.module.base.model.world.tilemap.Tilemaps;
import dev.peytob.rpg.core.module.base.resource.Tile;
import dev.peytob.rpg.math.vector.Vec2;
import dev.peytob.rpg.math.vector.Vec2i;
import dev.peytob.rpg.rpc.interfaces.base.model.Vec2RpcDto;
import dev.peytob.rpg.rpc.interfaces.base.model.Vec2iRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.model.world.TileRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.model.world.TilemapRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.model.world.WorldRpcDto;

import static dev.peytob.rpg.math.vector.Vectors.immutableVec2;
import static dev.peytob.rpg.math.vector.Vectors.immutableVec2i;

public enum RpcMessageMapper {;

    public static Vec2 toVec2(Vec2RpcDto vec2Rpc) {
        return immutableVec2(vec2Rpc.getX(), vec2Rpc.getY());
    }

    public static Vec2RpcDto toVec2Dto(Vec2 vec2) {
        return Vec2RpcDto.newBuilder()
            .setX(vec2.x())
            .setY(vec2.y())
            .build();
    }

    public static Vec2i toVec2i(Vec2iRpcDto vec2iRpc) {
        return immutableVec2i(vec2iRpc.getX(), vec2iRpc.getY());
    }

    public static Vec2iRpcDto toVec2iDto(Vec2i vec2i) {
        return Vec2iRpcDto.newBuilder()
            .setX(vec2i.x())
            .setY(vec2i.y())
            .build();
    }

    public static Tilemap toTilemap(TilemapRpcDto tilemapRpcDto) {
        Vec2i sizes = toVec2i(tilemapRpcDto.getSizes());
        Tilemap tilemap = Tilemaps.mutable(sizes);

        for (TileRpcDto tileRpcDto : tilemapRpcDto.getTilesList()) {
            Tile tile = new Tile(tileRpcDto.getTextId().hashCode(), tileRpcDto.getTextId());
            tilemap.setTile(tileRpcDto.getPosition().getX(), tileRpcDto.getPosition().getY(), tile);
        }

        return tilemap;
    }

    public static World toWorld(WorldRpcDto worldRpcDto) {
        return new World(toTilemap(worldRpcDto.getTilemap()));
    }
}
