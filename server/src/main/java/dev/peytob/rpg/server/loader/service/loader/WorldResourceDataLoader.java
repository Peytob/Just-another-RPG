package dev.peytob.rpg.server.loader.service.loader;

import dev.peytob.rpg.core.gameplay.model.world.World;
import dev.peytob.rpg.core.gameplay.model.world.tilemap.Tilemap;
import dev.peytob.rpg.core.repository.TilemapRepository;
import dev.peytob.rpg.server.loader.service.dto.RawWorldDto;
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
        Tilemap tilemap = tilemapRepository.getById(rawResource.tilemap());
        return new World(rawResource.id(), tilemap);
    }
}
