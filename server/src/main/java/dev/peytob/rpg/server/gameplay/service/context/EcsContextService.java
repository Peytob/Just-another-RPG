package dev.peytob.rpg.server.gameplay.service.context;

public interface EcsContextService {
    EcsContextRunner getEcsContextRunner(String ecsContextRunnerName);
}
