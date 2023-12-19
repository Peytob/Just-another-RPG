package dev.peytob.rpg.server.loader.service;

import dev.peytob.rpg.server.loader.configuration.FileStructureProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@EnableConfigurationProperties(FileStructureProperties.class)
@Service
@RequiredArgsConstructor
public class PropertiesFileStructureService implements FileStructureService {

    private final FileStructureProperties fileStructureProperties;

    @Override
    public Path getTilesDirectoryPath() {
        return fileStructureProperties.tiles();
    }

    @Override
    public Path getTilemapsDirectoryPath() {
        return fileStructureProperties.tilemaps();
    }

    @Override
    public Path getWorldsDirectoryPath() {
        return fileStructureProperties.worlds();
    }
}
