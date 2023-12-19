package dev.peytob.rpg.server.loader.dto;

import dev.peytob.rpg.math.vector.Vec2i;

import java.util.Collection;
import java.util.List;

public record RawTilemapDto(
    String id,
    String title,
    Vec2i size,
    List<RawTilemapLayerDto> layers
) {

    public record RawTilemapLayerDto(
        Collection<RawPlacedTileDto> tiles
    ) {

        public record RawPlacedTileDto(
            Vec2i position,
            String id
        ) {
        }
    }
}
