package dev.peytob.rpg.server.loader.pipeline;

import dev.peytob.rpg.core.gameplay.model.world.World;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import dev.peytob.rpg.server.loader.service.FileStructureService;
import dev.peytob.rpg.server.loader.service.TileLoader;
import dev.peytob.rpg.server.loader.service.WorldLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Collection;

@Component
@RequiredArgsConstructor
@Slf4j
public class WorldsLoadingInitializingStep implements InitializingPipelineStep {

    private final WorldLoader worldLoader;

    private final FileStructureService fileStructureService;

    @Override
    public void execute() {
        Path worldsDirectoryPath = fileStructureService.getWorldsDirectoryPath();
        Collection<World> worlds = worldLoader.loadWorlds(worldsDirectoryPath);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
