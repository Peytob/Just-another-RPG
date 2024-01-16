package dev.peytob.rpg.server.gameplay.service.context;

import dev.peytob.rpg.core.gameplay.resource.World;

public interface WorldContextService {

    WorldContextRunner getWorldContextRunner(String worldContextRunnerName);

    WorldContextRunner startWorldContextRunner(World world, String worldContextRunnerName);
}
