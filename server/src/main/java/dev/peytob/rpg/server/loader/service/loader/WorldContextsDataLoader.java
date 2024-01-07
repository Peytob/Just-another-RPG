package dev.peytob.rpg.server.loader.service.loader;

import dev.peytob.rpg.core.exception.ResourceNotFoundException;
import dev.peytob.rpg.core.gameplay.repository.WorldRepository;
import dev.peytob.rpg.core.gameplay.resource.World;
import dev.peytob.rpg.server.loader.dto.RawWorldContextDto;
import dev.peytob.rpg.server.loader.dto.WorldContextDto;
import dev.peytob.rpg.server.loader.service.provider.RawResourceDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
public class WorldContextsDataLoader extends AbstractResourceDataLoader<RawWorldContextDto, WorldContextDto> {

    private final WorldRepository worldRepository;

    public WorldContextsDataLoader(Collection<RawResourceDataProvider<RawWorldContextDto>> rawResourceDataProviders, WorldRepository worldRepository) {
        super(rawResourceDataProviders);
        this.worldRepository = worldRepository;
    }

    @Override
    protected WorldContextDto mapToResource(RawWorldContextDto rawResource) {
        World world = worldRepository.getById(rawResource.world())
            .orElseThrow(() -> new ResourceNotFoundException("World " + rawResource.world() + " not found"));

        return new WorldContextDto(world);
    }
}
