package dev.peytob.rpg.server.gameplay.service.context;

import dev.peytob.rpg.core.gameplay.resource.World;

public interface EcsContextService {

    EcsContextRunner getEcsContextRunner(String ecsContextRunnerName);

    EcsContextRunner startEcsContextRunner(World world, String ecsContextRunnerName);
}
