package dev.peytob.rpg.server.loader.service;

import dev.peytob.rpg.core.resource.Tile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;

@Service
@Slf4j
public class FileTilesetTileLoader implements TileLoader {

    @Override
    public Collection<Tile> loadTiles(Path tilesDirectoryPath) {
        log.info("Loading tiles from {} directory", tilesDirectoryPath);
        return Collections.emptyList();
    }
}
