package dev.peytob.rpg.server.loader.pipeline;

import dev.peytob.rpg.core.repository.TileRepository;
import dev.peytob.rpg.core.resource.Tile;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import dev.peytob.rpg.server.loader.service.FileStructureService;
import dev.peytob.rpg.server.loader.service.TileLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class TilesLoadingInitializingStep implements InitializingPipelineStep {

    private final TileLoader tileLoader;

    private final FileStructureService fileStructureService;

    private final TileRepository tileRepository;

    @Override
    public void execute() {
        Path tilesDirectoryPath = fileStructureService.getTilesDirectoryPath();
        Collection<Tile> tiles = tileLoader.loadTiles(tilesDirectoryPath);
        tiles.forEach(tileRepository::append);

        if (log.isDebugEnabled()) {
            String loadedTilesList = tiles.stream()
                .map(Tile::id)
                .collect(Collectors.joining(";\n"));

            log.debug("Loaded {} tiles. Ids list:\n", loadedTilesList);
        } else {
            log.info("Loaded {} tiles", tiles.size());
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
