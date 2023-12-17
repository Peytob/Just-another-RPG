package dev.peytob.rpg.server.loader.dto;

import dev.peytob.rpg.math.vector.Vec2i;

import java.util.Collection;
import java.util.List;

public record RawTilemapDto(
    Vec2i size,
    String title,
    String id,
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
