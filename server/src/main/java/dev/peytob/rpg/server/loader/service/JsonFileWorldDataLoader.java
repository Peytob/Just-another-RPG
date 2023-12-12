package dev.peytob.rpg.server.loader.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.peytob.rpg.core.gameplay.model.world.World;
import dev.peytob.rpg.core.gameplay.model.world.tilemap.Tilemap;
import dev.peytob.rpg.core.repository.TilemapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileFilter;
import java.io.Reader;

import static dev.peytob.rpg.server.loader.constants.DefaultFileFilters.JSON_FILE_FILTER;

@Service
@RequiredArgsConstructor
public class JsonFileWorldDataLoader extends FileDataLoader<World> {

    private final ObjectMapper objectMapper;

    private final TilemapRepository tilemapRepository;

    @Override
    protected World parseFile(Reader fileReader) throws Exception {
        JsonWorldDto worldDto = objectMapper.readValue(fileReader, JsonWorldDto.class);

        Tilemap tilemap = tilemapRepository.getById(worldDto.tilemap());

        return new World(worldDto.id(), tilemap);
    }

    @Override
    protected FileFilter getDirectoryFileFilter(File file) {
        return JSON_FILE_FILTER;
    }

    private record JsonWorldDto(
        String id,
        String tilemap
    ) {
    }
}
