package dev.peytob.rpg.server.loader.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Streams;
import dev.peytob.rpg.core.resource.Tile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileFilter;
import java.io.Reader;
import java.util.Collection;
import java.util.Collections;

import static dev.peytob.rpg.server.loader.constants.DefaultFileFilters.JSON_FILE_FILTER;

/**
 * Implements loading collection of tiles, because one file can contain array of tiles.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class JsonFileTileDataLoader extends FileDataLoader<Collection<Tile>> {

    private final ObjectMapper objectMapper;

    @Override
    protected Collection<Tile> parseFile(Reader fileReader) throws Exception {
        JsonNode root = objectMapper.readTree(fileReader);

        if (root.isArray()) {
            return Streams.stream(root.elements())
                .map(tileJsonNode -> objectMapper.convertValue(tileJsonNode, Tile.class))
                .toList();
        }

        if (root.isObject()) {
            Tile tile = objectMapper.treeToValue(root, Tile.class);
            return Collections.singleton(tile);
        }

        throw new IllegalArgumentException("Json file root should be an object or object array");
    }

    @Override
    protected FileFilter getDirectoryFileFilter(File file) {
        return JSON_FILE_FILTER;
    }
}
