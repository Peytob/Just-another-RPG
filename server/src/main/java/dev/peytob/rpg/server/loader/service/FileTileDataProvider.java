package dev.peytob.rpg.server.loader.service;

import dev.peytob.rpg.core.resource.Tile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class FileTileDataProvider implements DataProvider<Tile> {

    private final FileStructureService fileStructureService;

    private final FileDataLoader<Collection<Tile>> tileFileDataLoader;

    @Override
    public Collection<Tile> loadData() {
        return tileFileDataLoader
            .loadData(fileStructureService.getTilesDirectoryPath())
            .stream()
            .flatMap(Collection::stream).toList();
    }
}
