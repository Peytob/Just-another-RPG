package dev.peytob.rpg.server.loader.service;

import java.nio.file.Path;

public interface FileStructureService {

    Path getTilesDirectoryPath();

    Path getTilemapsDirectoryPath();

    Path getWorldsDirectoryPath();

    Path getWorldContextsPath();
}
