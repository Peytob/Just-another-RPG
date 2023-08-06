package dev.peytob.rpg.client.module.network.mapper;

import dev.peytob.rpg.core.module.base.model.level.tilemap.Tilemap;
import dev.peytob.rpg.math.vector.Vec2i;
import dev.peytob.rpg.rpc.interfaces.base.model.Vec2iRpcDto;
import dev.peytob.rpg.rpc.interfaces.base.model.world.TilemapRpcDto;
import org.springframework.stereotype.Component;

@Component
public interface GrpcDtoMapper {

    Vec2i toVec2i(Vec2iRpcDto dto);

    Tilemap toTilemap(TilemapRpcDto tilemapRpcDto);
}
