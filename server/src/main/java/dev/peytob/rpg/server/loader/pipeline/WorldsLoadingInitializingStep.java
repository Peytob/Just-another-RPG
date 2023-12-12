package dev.peytob.rpg.server.loader.pipeline;

import dev.peytob.rpg.core.gameplay.model.world.World;
import dev.peytob.rpg.core.repository.WorldRepository;
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
public class WorldsLoadingInitializingStep implements InitializingPipelineStep {

    private final Collection<DataProvider<World>> tileProviders;

    private final WorldRepository worldRepository;

    @Override
    public void execute() {
        Collection<World> worlds = tileProviders.stream()
            .map(DataProvider::loadData)
            .flatMap(Collection::stream)
            .toList();

        worlds.forEach(worldRepository::append);

        if (log.isDebugEnabled()) {
            String loadedTilesList = worlds.stream()
                .map(World::id)
                .collect(Collectors.joining(";\n"));

            log.debug("Loaded {} worlds. Ids list:\n", loadedTilesList);
        } else {
            log.info("Loaded {} worlds", worlds.size());
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
