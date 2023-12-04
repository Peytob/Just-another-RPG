package dev.peytob.rpg.server.loader.service;

import dev.peytob.rpg.core.gameplay.model.world.tilemap.Tilemap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class FileTilemapDataProvider implements DataProvider<Tilemap> {

    private final FileStructureService fileStructureService;

    private final FileDataLoader<Tilemap> tilemapsFileDataLoader;

    @Override
    public Collection<Tilemap> loadData() {
        return tilemapsFileDataLoader.loadData(fileStructureService.getTilemapsDirectoryPath());
    }
}
