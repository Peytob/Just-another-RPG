package dev.peytob.rpg.server.loader.service;

import dev.peytob.rpg.core.gameplay.model.world.World;

import java.nio.file.Path;
import java.util.Collection;

public interface WorldLoader {

    Collection<World> loadWorlds(Path worldDirectoryPath);
}
