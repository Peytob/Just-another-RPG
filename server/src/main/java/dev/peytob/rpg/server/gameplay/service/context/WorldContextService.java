package dev.peytob.rpg.server.gameplay.service.context;

import dev.peytob.rpg.core.gameplay.resource.World;

import java.util.Collection;

public interface WorldContextService {

    WorldContextRunner getWorldContextRunner(String worldContextRunnerName);

    Collection<WorldContextRunner> getAllWorldContextRunners();

    WorldContextRunner startWorldContextRunner(World world, String worldContextRunnerName);
}
