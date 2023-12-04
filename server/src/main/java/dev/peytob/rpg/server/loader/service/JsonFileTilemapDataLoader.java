package dev.peytob.rpg.server.loader.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.peytob.rpg.core.exception.ResourceNotFoundException;
import dev.peytob.rpg.core.gameplay.model.world.tilemap.Tilemap;
import dev.peytob.rpg.core.gameplay.model.world.tilemap.layer.TilemapLayer;
import dev.peytob.rpg.core.repository.TileRepository;
import dev.peytob.rpg.core.resource.Tile;
import dev.peytob.rpg.math.vector.Vec2i;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileFilter;
import java.io.Reader;
import java.util.Collection;
import java.util.List;

import static dev.peytob.rpg.server.loader.constants.DefaultFileFilters.JSON_FILE_FILTER;

@Service
@RequiredArgsConstructor
public class JsonFileTilemapDataLoader extends FileDataLoader<Tilemap> {

    private final ObjectMapper objectMapper;

    private final TileRepository tileRepository;

    @Override
    protected Tilemap parseFile(Reader fileReader) throws Exception {
        JsonTilemapDto tilemapDto = objectMapper.readValue(fileReader, JsonTilemapDto.class);
        int layersCount = tilemapDto.layers().size();

        Tilemap tilemap = Tilemap.create(tilemapDto.size(), layersCount, tilemapDto.title(), tilemapDto.id());

        for (int layerIndex = 0; layerIndex < layersCount; layerIndex++) {
            JsonTilemapLayer jsonTilemapLayer = tilemapDto.layers().get(layerIndex);
            TilemapLayer tilemapLayer = tilemap.getMutableLayer(layerIndex);

            for (JsonPlacedTileDto placedTile : jsonTilemapLayer.tiles()) {
                Tile tile = tileRepository.getById(placedTile.id());

                if (tile == null) {
                    throw new ResourceNotFoundException("Tile with id " + placedTile.id + " not found");
                }

                tilemapLayer.setTile(placedTile.position(), tile);
            }
        }

        return tilemap;
    }

    @Override
    protected FileFilter getDirectoryFileFilter(File file) {
        return JSON_FILE_FILTER;
    }

    private record JsonPlacedTileDto(
        Vec2i position,
        String id
    ) {
    }

    private record JsonTilemapLayer(
        Collection<JsonPlacedTileDto> tiles
    ) {
    }

    private record JsonTilemapDto(
        Vec2i size,
        String title,
        String id,
        List<JsonTilemapLayer> layers
    ) {
    }
}
