package dev.peytob.rpg.server.loader.service.loader;

import dev.peytob.rpg.core.resource.Tile;
import dev.peytob.rpg.server.loader.service.dto.RawTileDto;
import dev.peytob.rpg.server.loader.service.provider.RawResourceDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
public class TileResourceDataLoader extends AbstractResourceDataLoader<RawTileDto, Tile> {

    public TileResourceDataLoader(Collection<RawResourceDataProvider<RawTileDto>> rawResourceDataProviders) {
        super(rawResourceDataProviders);
    }

    @Override
    protected Tile mapToResource(RawTileDto rawResource) {
        return new Tile(rawResource.id());
    }
}
