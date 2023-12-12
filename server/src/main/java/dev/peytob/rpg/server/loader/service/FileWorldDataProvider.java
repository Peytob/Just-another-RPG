package dev.peytob.rpg.server.loader.service;

import dev.peytob.rpg.core.gameplay.model.world.World;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class FileWorldDataProvider implements DataProvider<World> {

    private final FileStructureService fileStructureService;

    private final FileDataLoader<World> worldFileDataProvider;

    @Override
    public Collection<World> loadData() {
        return worldFileDataProvider.loadData(fileStructureService.getWorldsDirectoryPath());
    }
}
