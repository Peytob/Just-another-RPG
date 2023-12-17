package dev.peytob.rpg.server.gameplay.service.context;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@Service
public class EcsContextServiceImpl implements EcsContextService {

    private final Map<String, EcsContextRunner> ecsContextRunners;

    public EcsContextServiceImpl(Collection<EcsContextRunner> ecsContextRunners) {
        this.ecsContextRunners = ecsContextRunners.stream()
            .collect(Collectors.toMap(EcsContextRunner::getContextName, identity()));
    }

    @Override
    public EcsContextRunner getEcsContextRunner(String ecsContextRunnerName) {
        EcsContextRunner ecsContextRunner = ecsContextRunners.get(ecsContextRunnerName);

        if (ecsContextRunner == null) {
            throw new IllegalStateException("ECS context runner with name %s not found".formatted(ecsContextRunnerName));
        }

        return ecsContextRunner;
    }
}
