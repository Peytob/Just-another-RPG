package dev.peytob.rpg.server.loader.service;

import dev.peytob.rpg.core.resource.Tile;

import java.nio.file.Path;
import java.util.Collection;

public interface TileLoader {

    Collection<Tile> loadTiles(Path tilesDirectoryPath);
}
