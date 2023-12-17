package dev.peytob.rpg.server.loader.service.loader;

import dev.peytob.rpg.core.exception.ResourceNotFoundException;
import dev.peytob.rpg.core.gameplay.resource.World;
import dev.peytob.rpg.core.gameplay.resource.tilemap.Tilemap;
import dev.peytob.rpg.core.gameplay.repository.TilemapRepository;
import dev.peytob.rpg.server.loader.dto.RawWorldDto;
import dev.peytob.rpg.server.loader.service.provider.RawResourceDataProvider;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class WorldResourceDataLoader extends AbstractResourceDataLoader<RawWorldDto, World> {

    private final TilemapRepository tilemapRepository;

    public WorldResourceDataLoader(Collection<RawResourceDataProvider<RawWorldDto>> rawResourceDataProviders, TilemapRepository tilemapRepository) {
        super(rawResourceDataProviders);
        this.tilemapRepository = tilemapRepository;
    }

    @Override
    protected World mapToResource(RawWorldDto rawResource) {
        Tilemap tilemap = tilemapRepository.getById(rawResource.tilemap()).orElseThrow(() ->
            new ResourceNotFoundException("Tilemap with id " + rawResource.tilemap() + " not found during loading world " + rawResource.id()));

        return new World(rawResource.id(), tilemap);
    }
}
