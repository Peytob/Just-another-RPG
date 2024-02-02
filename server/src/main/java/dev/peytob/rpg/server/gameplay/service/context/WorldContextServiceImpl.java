package dev.peytob.rpg.server.gameplay.service.context;

import dev.peytob.rpg.core.gameplay.resource.World;
import dev.peytob.rpg.ecs.context.EcsContexts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@Service
@Slf4j
public class WorldContextServiceImpl implements WorldContextService {

    private final Map<String, WorldContextRunner> worldContextRunners;

    private final ExecutorService executorService;

    public WorldContextServiceImpl(ExecutorService executorService) {
        this.executorService = executorService;
        this.worldContextRunners = new HashMap<>();
    }

    @Override
    public WorldContextRunner getWorldContextRunner(String worldContextRunnerName) {
        WorldContextRunner worldContextRunner = worldContextRunners.get(worldContextRunnerName);

        if (worldContextRunner == null) {
            throw new IllegalStateException("World context runner with name " + worldContextRunnerName + " not found");
        }

        return worldContextRunner;
    }

    @Override
    public Collection<WorldContextRunner> getAllWorldContextRunners() {
        return worldContextRunners.values();
    }

    @Override
    public WorldContextRunner startWorldContextRunner(World world, String worldContextRunnerName) {
        log.info("Creating new world context runner with name {} based on world with id {}", worldContextRunnerName, world.id());

        if (worldContextRunners.containsKey(worldContextRunnerName)) {
            throw new IllegalArgumentException("World context with name " + worldContextRunnerName + " already exists");
        }

        AsyncWorldContextRunner worldContextRunner = new AsyncWorldContextRunner(EcsContexts.empty(), worldContextRunnerName);
        executorService.submit(worldContextRunner);

        return worldContextRunners.put(worldContextRunner.getContextName(), worldContextRunner);
    }
}
