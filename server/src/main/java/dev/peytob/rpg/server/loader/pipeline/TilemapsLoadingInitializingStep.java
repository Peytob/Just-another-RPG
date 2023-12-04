package dev.peytob.rpg.server.loader.pipeline;

import dev.peytob.rpg.core.gameplay.model.world.tilemap.Tilemap;
import dev.peytob.rpg.core.repository.TileRepository;
import dev.peytob.rpg.core.repository.TilemapRepository;
import dev.peytob.rpg.core.resource.Tile;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import dev.peytob.rpg.server.loader.service.DataProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class TilemapsLoadingInitializingStep implements InitializingPipelineStep {

    private final Collection<DataProvider<Tilemap>> tilemapProviders;

    private final TilemapRepository tilemapRepository;

    @Override
    public void execute() {
        Collection<Tilemap> tilemaps = tilemapProviders.stream()
            .map(DataProvider::loadData)
            .flatMap(Collection::stream)
            .toList();

        tilemaps.forEach(tilemapRepository::append);

        if (log.isDebugEnabled()) {
            String loadedTilemapsList = tilemaps.stream()
                .map(Tilemap::id)
                .collect(Collectors.joining(";\n"));

            log.debug("Loaded {} tilemaps. Ids list:\n", loadedTilemapsList);
        } else {
            log.info("Loaded {} tilemaps", tilemaps.size());
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
