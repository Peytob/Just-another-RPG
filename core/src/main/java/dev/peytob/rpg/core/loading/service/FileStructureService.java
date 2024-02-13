package dev.peytob.rpg.core.loading.service;

import java.nio.file.Path;

public interface FileStructureService {

    Path getTilesDirectoryPath();

    Path getTilemapsDirectoryPath();

    Path getWorldsDirectoryPath();

    Path getWorldContextsPath();

    Path getTexturesPath();
}
