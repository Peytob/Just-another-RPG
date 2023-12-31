package dev.peytob.rpg.server.gameplay.service.context;

import dev.peytob.rpg.core.gameplay.resource.World;
import dev.peytob.rpg.ecs.context.EcsContexts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@Service
@Slf4j
public class EcsContextServiceImpl implements EcsContextService {

    private final Map<String, EcsContextRunner> ecsContextRunners;

    private final ExecutorService executorService;

    public EcsContextServiceImpl(ExecutorService executorService) {
        this.executorService = executorService;
        this.ecsContextRunners = new HashMap<>();
    }

    @Override
    public EcsContextRunner getEcsContextRunner(String ecsContextRunnerName) {
        EcsContextRunner ecsContextRunner = ecsContextRunners.get(ecsContextRunnerName);

        if (ecsContextRunner == null) {
            throw new IllegalStateException("ECS context runner with name " + ecsContextRunnerName + " not found");
        }

        return ecsContextRunner;
    }

    @Override
    public EcsContextRunner startEcsContextRunner(World world, String ecsContextRunnerName) {
        log.info("Creating new ECS context runner with name {} based on world with id {}", ecsContextRunnerName, world.id());

        if (ecsContextRunners.containsKey(ecsContextRunnerName)) {
            throw new IllegalArgumentException("Ecs context with name " + ecsContextRunnerName + " already exists");
        }

        AsyncEcsContextRunner ecsContextRunner = new AsyncEcsContextRunner(EcsContexts.empty(), ecsContextRunnerName);
        executorService.submit(ecsContextRunner);

        return ecsContextRunners.put(ecsContextRunner.getContextName(), ecsContextRunner);
    }
}
