package dev.peytob.rpg.server.loader.service.loader;

import dev.peytob.rpg.core.exception.ResourceNotFoundException;
import dev.peytob.rpg.core.gameplay.model.world.tilemap.Tilemap;
import dev.peytob.rpg.core.gameplay.model.world.tilemap.layer.TilemapLayer;
import dev.peytob.rpg.core.repository.TileRepository;
import dev.peytob.rpg.core.resource.Tile;
import dev.peytob.rpg.server.loader.service.dto.RawTilemapDto;
import dev.peytob.rpg.server.loader.service.provider.RawResourceDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
public class TilemapResourceDataLoader extends AbstractResourceDataLoader<RawTilemapDto, Tilemap> {

    private final TileRepository tileRepository;

    public TilemapResourceDataLoader(Collection<RawResourceDataProvider<RawTilemapDto>> rawResourceDataProviders, TileRepository tileRepository) {
        super(rawResourceDataProviders);
        this.tileRepository = tileRepository;
    }

    @Override
    protected Tilemap mapToResource(RawTilemapDto rawResource) {
        int layersCount = rawResource.layers().size();

        Tilemap tilemap = Tilemap.create(rawResource.size(), layersCount, rawResource.title(), rawResource.id());

        for (int layerIndex = 0; layerIndex < layersCount; layerIndex++) {
            RawTilemapDto.RawTilemapLayerDto jsonTilemapLayer = rawResource.layers().get(layerIndex);
            TilemapLayer tilemapLayer = tilemap.getMutableLayer(layerIndex);

            for (RawTilemapDto.RawTilemapLayerDto.RawPlacedTileDto placedTile : jsonTilemapLayer.tiles()) {
                Tile tile = tileRepository.getById(placedTile.id());

                if (tile == null) {
                    throw new ResourceNotFoundException("Tile with id " + placedTile.id() + " not found during loading tilemap " + rawResource.id());
                }

                tilemapLayer.setTile(placedTile.position(), tile);
            }
        }

        return tilemap;
    }
}
