package dev.peytob.rpg.server.loader.service;

import dev.peytob.rpg.core.gameplay.model.world.World;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;

@Service
@Slf4j
public class FileWorldLoader implements WorldLoader {

    @Override
    public Collection<World> loadWorlds(Path worldDirectoryPath) {
        log.info("Loading worlds from {} directory", worldDirectoryPath);
        return Collections.emptyList();
    }
}
